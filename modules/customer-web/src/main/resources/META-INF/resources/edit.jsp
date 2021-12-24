<%@ include file="./init.jsp" %>

<portlet:actionURL var="addCustomerUrl" />

<%
    String redirect = ParamUtil.getString(request, "redirect");

    Calendar today = CalendarFactoryUtil.getCalendar(timeZone, locale);
    int birthDateDay = today.get(Calendar.DATE);
    int birthDateMonth = today.get(Calendar.MONTH);
    int birthDateYear = today.get(Calendar.YEAR);
%>

<aui:form action="<%= addCustomerUrl %>" name="<portlet:namespace />registerForm">
    <aui:input name="redirect" type="hidden" value="<%= themeDisplay.getURLCurrent() %>" />

    <aui:fieldset>
        <aui:input id="firstName" name="firstName" type="text" size="20"/>
        <aui:input id="lastName" name="lastName" type="text" size="50" />
        <aui:field-wrapper label="birth-date">
            <liferay-ui:input-date
                dayParam="birthDateDay"
                dayValue="<%= birthDateDay %>"
                monthParam="birthDateMonth"
                monthValue="<%= birthDateMonth %>"
                name="birthDate"
                yearParam="birthDateYear"
                yearValue="<%= birthDateYear %>"
            />

        </aui:field-wrapper>
        <aui:input id="emailAddress" name="emailAddress" type="email" />
    </aui:fieldset>
    <aui:button-row>
        <aui:button type="submit" />
        <aui:button href="<%= redirect %>" type="cancel" />
    </aui:button-row>
</aui:form>