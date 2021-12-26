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


    <liferay-ui:error exception="<%= CaptchaException.class %>" message="captcha-verification-failed" />
    <liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />

    <aui:fieldset>
        <aui:input id="firstName" name="firstName" type="text" />

        <aui:input id="lastName" name="lastName" type="text" />

        <aui:field-wrapper>
            <label class="control-label" for="<portlet:namespace />birthDate" >
                <liferay-ui:message key="birth-date" />
            </label>
            <liferay-ui:input-date
                dayParam="birthDateDay"
                monthParam="birthDateMonth"
                name="birthDate"
                yearParam="birthDateYear"
                nullable="true"
                showDisableCheckbox="false"
            />
        </aui:field-wrapper>

        <aui:input id="emailAddress" name="emailAddress" type="email" />

        <liferay-captcha:captcha />
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" />
        <aui:button href="<%= redirect %>" type="cancel" />
    </aui:button-row>
</aui:form>