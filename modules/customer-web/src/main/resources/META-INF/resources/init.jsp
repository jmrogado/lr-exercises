<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/captcha" prefix="liferay-captcha" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.jmrogado.liferay.customer.model.Customer"%>
<%@ page import="com.jmrogado.liferay.customer.constants.CustomerPortletKeys"%>
<%@ page import="com.jmrogado.liferay.customer.service.CustomerLocalService"%>
<%@ page import="com.jmrogado.liferay.customer.exception.DuplicateCustomerException"%>

<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>

<%@ page import="java.text.Format"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.captcha.CaptchaException" %>
<%@ page import="com.liferay.portal.kernel.captcha.CaptchaTextException" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    CustomerLocalService customerLocalService = (CustomerLocalService)request.getAttribute(CustomerPortletKeys.SVC_CUSTOMERS);
%>