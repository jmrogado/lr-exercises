package com.jmrogado.liferay.customer.portlet;

import com.jmrogado.liferay.customer.constants.CustomerPortletKeys;
import com.jmrogado.liferay.customer.model.Customer;
import com.jmrogado.liferay.customer.service.CustomerLocalService;
import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.portlet.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author jmrogado
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Customer",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CustomerPortletKeys.CUSTOMER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CustomerPortlet extends MVCPortlet {
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		List<Customer> customers = _customerLocalService.getCustomers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		renderRequest.setAttribute(CustomerPortletKeys.ATTR_CUSTOMERS, customers);
		renderRequest.setAttribute(CustomerPortletKeys.SVC_CUSTOMERS, _customerLocalService);
		super.render(renderRequest, renderResponse);
	}

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		try {
			CaptchaUtil.check(actionRequest);
			addCustomer(actionRequest);
			SessionMessages.add(actionRequest, "customer-added");

		} catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass());
			actionResponse.getRenderParameters().setValue("mvcPath", "/edit.jsp");
		}
	}

	private void sendMail(Customer customer, Locale locale) throws AddressException {
		ResourceBundle bundleResourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());

		MailMessage mailMessage = new MailMessage();
		mailMessage.setFrom(new InternetAddress("no-reply@liferay.com"));
		mailMessage.setTo(new InternetAddress(customer.getEmailAddress()));
		mailMessage.setSubject(LanguageUtil.get(bundleResourceBundle, "registry-successful"));
		mailMessage.setBody(LanguageUtil.get(bundleResourceBundle, "thanks-for-registering"));

		_mailService.sendEmail(mailMessage);
	}

	private void addCustomer(ActionRequest actionRequest) throws PortalException {
		Date now = new Date();

		Locale locale = PortalUtil.getLocale(actionRequest);

		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		Date birthDate = ParamUtil.getDate(actionRequest, "birthDate",
				DateFormatFactoryUtil.getDate(locale, null));
		String emailAddress = ParamUtil.getString(actionRequest, "emailAddress");

		Customer customer = _customerLocalService.createCustomer(0);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setBirthDate(birthDate);
		customer.setEmailAddress(emailAddress);
		customer.setCreateDate(now);
		customer.setModifiedDate(now);
		customer.setCompanyId(PortalUtil.getCompanyId(actionRequest));
		customer.setGroupId(PortalUtil.getScopeGroupId(actionRequest));

		_customerLocalService.addCustomer(customer);

		try {
			sendMail(customer, locale);
		} catch (AddressException e) {
			e.printStackTrace();
		}

	}

	@Reference
	CustomerLocalService _customerLocalService;

	@Reference
	MailService _mailService;
}
