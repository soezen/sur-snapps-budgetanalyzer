<%--
  Created by IntelliJ IDEA.
  User: Soezen
  Date: 6/04/14
  Time: 10:36
--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sur:template>
    <jsp:attribute name="title"><fmt:message key="title.user_registration" /></jsp:attribute>
    <jsp:body>
        <c:if test="${empty success}">
            <!-- TODO send verification email or confirmation email? -->
            <f:form modelAttribute="user" commandName="user" action="postUserRegistration" method="post" cssClass="form-horizontal">
                <f:errors cssClass="form_error" />
                <f:hidden path="tokenValue" />
                <c:set var="withToken" value="${not empty user.tokenValue}" />
                <sur-form:form-fieldset legendKey="form.user_registration.legend">
                    <jsp:attribute name="formActions">
                        <a id="btn_cancel" class="btn" href="<c:url value="/" />"><fmt:message key="form.action.cancel" /></a>
                        <input id="btn_submit" class="btn btn-primary" type="submit" value="<fmt:message key="form.action.submit" />" />
                    </jsp:attribute>
                    <jsp:body>
                        <sur-form:form-property-input property="user" path="name" />
                        <sur-form:form-property-input property="user" path="username" />
                        <sur-form:form-property-input property="user" path="email" type="email" readonly="${withToken}" />
                        <sur-form:form-property-input property="user" path="newPassword" type="password" />
                        <sur-form:form-property-input property="user" path="confirmPassword" type="password" />
                    </jsp:body>
                </sur-form:form-fieldset>
            </f:form>
        </c:if>
    </jsp:body>
</sur:template>