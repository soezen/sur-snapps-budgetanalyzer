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

<sur:template>
    <jsp:attribute name="title"><fmt:message key="title.profile" /></jsp:attribute>
    <jsp:body>
        <div class="form-horizontal">
            <sur-form:form-fieldset legendKey="form.profile.legend">
                <jsp:attribute name="formActions">
                    <a class="btn btn-default btn-block"><fmt:message key="form.profile.action.close_user" /></a>
                </jsp:attribute>
                <jsp:body>
                    <sur-form:form-property name="user" property="${user}" edit_property="${editUser}" />
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
                <jsp:attribute name="extension">
                    <sur:accordion id="entity_accordion">
                        <sur:accordion-panel parentId="entity_accordion" id="users_accordion_panel">
                            <jsp:attribute name="title">
                                Other users of ${user.entity().name}
                            </jsp:attribute>
                            <jsp:body>
                                <jsp:include page="entity_users_table.jsp" />
                            </jsp:body>
                        </sur:accordion-panel>
                        <sec:authorize ifAllGranted="ROLE_ADMIN">
                            <sur:accordion-panel parentId="entity_accordion" id="tokens_accordion_panel">
                                <jsp:attribute name="title">Outgoing invitations to join entity</jsp:attribute>
                                <jsp:body>
                                    <jsp:include page="entity_tokens_table.jsp" />
                                </jsp:body>
                            </sur:accordion-panel>
                        </sec:authorize>
                    </sur:accordion>
                </jsp:attribute>
                <jsp:attribute name="formActions">
                    <c:if test="${user.admin}">
                        <a id="disable_entity" class="btn btn-default"><fmt:message key="form.profile.action.disable_entity" /></a>
                    </c:if>
                </jsp:attribute>
                <jsp:body>
                    <sur-form:form-property name="entity" property="${user.entity}" edit_property="${editEntity}" />
                    <c:choose>
                        <c:when test="${user.admin}">
                            <sur-form:form-edit-group path="name" />
                        </c:when>
                        <c:otherwise>
                            <sur-form:form-property-output path="name" />
                        </c:otherwise>
                    </c:choose>
                </jsp:body>
            </sur-form:form-fieldset>
            <!-- TODO don't show empty tables -->
        </div>
        <!-- TODO-FUNC UC-1 in popup and make user confirm, add help text so user knows what the action implies -->

    </jsp:body>
</sur:template>