<%@ include file="./init.jsp" %>

<portlet:actionURL var="addCustomerUrl" />

<%
    String redirect = ParamUtil.getString(request, "redirect");
%>

<aui:form action="<%= addCustomerUrl %>" name="<portlet:namespace />registerForm">
    <aui:input name="redirect" type="hidden" value="<%= themeDisplay.getURLCurrent() %>" />

    <liferay-ui:error exception="<%= CaptchaException.class %>" message="captcha-verification-failed" />
    <liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
    <liferay-ui:error exception="<%= DuplicateCustomerException.class %>" message="email-address-already-registered" />

    <aui:fieldset>
        <aui:input id="firstName" name="firstName" type="text">
            <aui:validator name="required" />
            <aui:validator name="custom"
                errorMessage="please-enter-only-alpha-characters">
                function(val, fieldNode, ruleValue) {
                    var regex = new RegExp(/^[a-z\-\s]+$/i);
                    return regex.test(val);
                }
            </aui:validator>
        </aui:input>

        <aui:input id="lastName" name="lastName" type="text">
            <aui:validator name="required" />
            <aui:validator name="custom"
                errorMessage="please-enter-only-alpha-characters">
                function(val, fieldNode, ruleValue) {
                    var regex = new RegExp(/^[a-z\-\s]+$/i);
                    return regex.test(val);
                }
            </aui:validator>
        </aui:input>

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

        <aui:input id="emailAddress" name="emailAddress" type="email">
            <aui:validator name="required" />
            <aui:validator name="email" />
        </aui:input>

        <liferay-captcha:captcha />
    </aui:fieldset>

    <aui:button-row>
        <aui:button type="submit" />
        <aui:button href="<%= redirect %>" type="cancel" />
    </aui:button-row>
</aui:form>