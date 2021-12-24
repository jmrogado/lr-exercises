package com.jmrogado.liferay.customer.portlet;

import com.jmrogado.liferay.customer.constants.CustomerPortletKeys;
import com.jmrogado.liferay.customer.model.Customer;
import com.jmrogado.liferay.customer.service.CustomerLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.util.Date;
import java.util.List;

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
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		Date now = new Date();

		try {
			String firstName = ParamUtil.getString(actionRequest, "firstName");
			String lastName = ParamUtil.getString(actionRequest, "lastName");
			Date birthDate = ParamUtil.getDate(actionRequest, "birthDate",
					DateFormatFactoryUtil.getDate(PortalUtil.getLocale(actionRequest), null));
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

		} catch (PortalException portalException) {
			portalException.printStackTrace();
		}

		super.processAction(actionRequest, actionResponse);
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		List<Customer> customers = _customerLocalService.getCustomers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		renderRequest.setAttribute(CustomerPortletKeys.ATTR_CUSTOMERS, customers);
		renderRequest.setAttribute(CustomerPortletKeys.SVC_CUSTOMERS, _customerLocalService);
		super.render(renderRequest, renderResponse);
	}

	@Reference
	CustomerLocalService _customerLocalService;
}