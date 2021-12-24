<%@ include file="./init.jsp" %>

<portlet:renderURL var="editUrl">
    <portlet:param name="mvcPath" value="/edit.jsp" />
    <portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
</portlet:renderURL>

<%
    List<Customer> customers = (List)request.getAttribute(CustomerPortletKeys.ATTR_CUSTOMERS);
    Format fdf = FastDateFormatFactoryUtil.getDate(DateFormat.MEDIUM, locale, timeZone);
%>

<p>
    Hello Customers!
</p>

<liferay-ui:search-container total="<%= customers.size() %>">
    <liferay-ui:search-container-results results="<%= customers %>" />
    <liferay-ui:search-container-row className="com.jmrogado.liferay.customer.model.Customer" modelVar="customer">
        <liferay-ui:search-container-column-text property="firstName" />
        <liferay-ui:search-container-column-text property="lastName" />
        <liferay-ui:search-container-column-text name="birthDatexxx" value="<%= fdf.format(customer.getBirthDate()) %>" />

        <liferay-ui:search-container-column-text property="emailAddress" />
        <liferay-ui:search-container-column-date property="createDate" />
    </liferay-ui:search-container-row>
    <liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:button-row>
    <aui:button onClick="<%= editUrl.toString() %>" value="Register" />
</aui:button-row>