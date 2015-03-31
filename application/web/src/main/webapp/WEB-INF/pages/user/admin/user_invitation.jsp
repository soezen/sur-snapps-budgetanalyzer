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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sur:dashboard>
    <h1><fmt:message key="title.user_invitation" /></h1>
    <f:form modelAttribute="user" action="postInviteUser" method="post">
        <div class="form-horizontal">
            <sur-form:form-fieldset legendKey="form.user_invitation.legend">
                <jsp:attribute name="formActions">
                    <a id="btn_cancel" class="btn" href="<c:url value="/budgetanalyzer/user/profile" />"><fmt:message key="form.action.cancel" /></a>
                    <input id="btn_submit" class="btn btn-primary" type="submit" value="<fmt:message key="form.action.submit" />" />
                </jsp:attribute>
                <jsp:body>
                    <sur-form:form-property-input path="email" property="user" />
                </jsp:body>
            </sur-form:form-fieldset>
        </div>
    </f:form>
</sur:dashboard>


<!-- Scenario: -->
<!-- 1. user create account -->
<!-- -> entity is created for that user -->
<!-- 2. user invites other user to have access to that entity -->
<!-- -> mail is sent to invitee -->
<!-- 3. user accepts invitation -->
<!-- -> user is created and linked to existing entity -->
<!-- : nice to have or not? -->
<!-- -> if user already has an account: give user option to not create a new user but join his existing entity with the other one: who will be admin then, -->
<!-- -> this will also mean that other user now also has access to new users accounts -->

<!-- TODO form template with java classes -->
