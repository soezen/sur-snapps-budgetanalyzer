<%--
  Created by IntelliJ IDEA.
  User: Soezen
  Date: 6/04/14
  Time: 10:36
--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:template>
    <jsp:attribute name="title">User Registration</jsp:attribute>
    <jsp:body>
        <c:if test="${empty success}">
            <!-- TODO send verification email or confirmation email? -->
            <f:form modelAttribute="user" action="postUserRegistration" method="post" cssClass="form-horizontal">
                <f:errors cssClass="form_error" />
                <f:hidden path="tokenValue" />
                <c:set var="withToken" value="${not empty user.tokenValue}" />
                <t:form-fieldset legendKey="form.user_registration.legend">
                    <jsp:attribute name="formActions">
                        <a id="btn_cancel" class="btn" href="<c:url value="/" />"><fmt:message key="form.action.cancel" /></a>
                        <input id="btn_submit" class="btn btn-primary" type="submit" value="<fmt:message key="form.action.submit" />" />
                    </jsp:attribute>
                    <jsp:body>
                        <t:form-property-input property="user" path="name" />
                        <t:form-property-input property="user" path="username" />
                        <t:form-property-input property="user" path="email" type="email" readonly="${withToken}" />
                        <t:form-property-input path="password" property="user" type="password" />
                    </jsp:body>
                </t:form-fieldset>
            </f:form>
        </c:if>
    </jsp:body>
</t:template>