<%@ include file="./init.jsp" %>

<portlet:renderURL var="editUrl">
    <portlet:param name="mvcPath" value="/edit.jsp" />
    <portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
</portlet:renderURL>

<%
    List<Customer> customers = (List)request.getAttribute(CustomerPortletKeys.ATTR_CUSTOMERS);
    Format fdf = FastDateFormatFactoryUtil.getDate(DateFormat.MEDIUM, locale, timeZone);
%>

<liferay-ui:success key="customer-added" message="customer-added-successfully" />

<liferay-ui:search-container
        total="<%= (int)request.getAttribute(CustomerPortletKeys.ATTR_TOTAL) %>"
        emptyResultsMessage="no-registered-customers"
        delta="<%= (int)request.getAttribute(CustomerPortletKeys.ATTR_DELTA) %>" >

    <liferay-ui:search-container-results results="<%= customers %>" />

    <liferay-ui:search-container-row className="com.jmrogado.liferay.customer.model.Customer" modelVar="customer">
        <liferay-ui:search-container-column-text name="first-name" property="firstName" />
        <liferay-ui:search-container-column-text name="last-name" property="lastName" />
        <liferay-ui:search-container-column-text name="birth-date" value="<%= fdf.format(customer.getBirthDate()) %>" />
        <liferay-ui:search-container-column-text name="email-address" property="emailAddress" />
        <liferay-ui:search-container-column-date name="create-date" property="createDate" />
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator />

</liferay-ui:search-container>

<aui:button-row>
    <aui:button onClick="<%= editUrl.toString() %>" value="Register" />
</aui:button-row>