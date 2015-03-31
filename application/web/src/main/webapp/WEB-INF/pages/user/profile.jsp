<%--
  Created by IntelliJ IDEA.
  User: Soezen
  Date: 6/04/14
  Time: 10:36
--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>
<!-- TODO invite new user should be a button -->
<sur:dashboard>
    <h1><fmt:message key="title.profile" /></h1>
    <div class="form-horizontal">
        <sur-form:form-fieldset legendKey="form.profile.legend">
            <jsp:attribute name="formActions">
                <a class="btn btn-default btn-block"><fmt:message key="form.profile.action.close_user" /></a>
            </jsp:attribute>
            <jsp:body>
                <sur-form:form-property name="user" property="${currentUser}" edit_property="${editUser}" />
                <sur-form:form-property-output path="username" />
                <sur-form:form-edit-group path="name" />
                <sur-form:form-edit-group path="emailAddress" input_fields="email" />
                <sur-form:form-edit-group path="password" type="password" input_fields="newPassword,confirmPassword" />
                <!-- TODO clean up leftovers from form-focus en form-edit group -->
            </jsp:body>
        </sur-form:form-fieldset>
    </div>
    <div class="form-horizontal">
        <sur-form:form-fieldset legendKey="form.profile.entity.legend">
            <jsp:body>
                <sur-form:form-property name="entity" property="${currentUser.entity}" edit_property="${editEntity}" />
                <c:choose>
                    <c:when test="${currentUser.admin}">
                        <sur-form:form-edit-group path="name" />
                    </c:when>
                    <c:otherwise>
                        <sur-form:form-property-output path="name" />
                    </c:otherwise>
                </c:choose>
            </jsp:body>
        </sur-form:form-fieldset>
        <jsp:include page="entity_users_table.jsp" />
        <!-- TODO don't show empty tables -->
        <!-- TODO replace table with something else here -->
    </div>
    <!-- TODO-FUNC UC-1 in popup and make user confirm, add help text so user knows what the action implies -->
</sur:dashboard>