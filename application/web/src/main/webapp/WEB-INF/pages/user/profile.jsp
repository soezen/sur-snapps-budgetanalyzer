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

<sur:template>
    <jsp:attribute name="title"><fmt:message key="title.profile" /></jsp:attribute>
    <jsp:body>
        <f:form modelAttribute="editUser" action="postEditUser" method="post">
        <div class="form-horizontal">
            <sur:form-fieldset legendKey="form.profile.legend">
                <jsp:attribute name="formActions">
                    <a class="btn btn-default btn-block"><fmt:message key="form.profile.action.close_user" /></a>
                </jsp:attribute>
                <jsp:body>
                    <sur:form-property-output path="username" property="${user}" />
                    <sur:form-property-input path="username" property="editUser" visible="false" />
                    <sur:form-property-output path="name" property="${user}" editable="true" edit_group="name" />
                    <sur:form-property-input path="name" property="editUser" visible="false" show_buttons="true" edit_group="name" />
                    <sur:form-property-output path="email.address" property="${user}" editable="true" edit_group="email" />
                    <sur:form-property-input path="email" property="editUser" visible="false" show_buttons="true" edit_group="email" />
                    <sur:form-property-output path="password" property="${user}" type="password" editable="true" edit_group="password" />
                    <sur:form-property-input path="newPassword" property="editUser" visible="false" edit_group="password" />
                    <sur:form-property-input path="confirmPassword" property="editUser" visible="false" show_buttons="true" edit_group="password" />
                    <!-- TODO allow to specify list in edit_group -->
                </jsp:body>
            </sur:form-fieldset>
        </div>
        </f:form>
        <div class="form-horizontal">
            <sur:form-fieldset legendKey="form.profile.entity.legend">
                <jsp:attribute name="extension">
                    <sur:accordion id="entity_accordion">
                        <sur:accordion-panel parentId="entity_accordion" id="users_accordion_panel">
                            <jsp:attribute name="title">
                                Other users of ${user.entity.name}
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
                    <sur:form-property-output path="name" property="${user.entity}" />
                </jsp:body>
            </sur:form-fieldset>
        </div>
        <!-- TODO-FUNC UC-1 in popup and make user confirm, add help text so user knows what the action implies -->


        <a id="btn_reload_page" href="<c:url value="/budgetanalyzer/user/profile" />">Reload page</a>
    </jsp:body>
</sur:template>