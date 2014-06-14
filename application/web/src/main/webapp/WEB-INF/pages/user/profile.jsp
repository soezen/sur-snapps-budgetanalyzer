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
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <jsp:attribute name="title">User Profile</jsp:attribute>
    <jsp:body>
        <div class="form-horizontal">
            <t:form-fieldset legendKey="form.profile.legend">
                <jsp:attribute name="formActions">
                    <a class="btn btn-default btn-block">Edit User Info</a>
                    <a class="btn btn-default btn-block">Close User Account</a>
                </jsp:attribute>
                <jsp:body>
                    <t:form-property-output property="${user}" path="name" />
                    <t:form-property-output path="username" property="${user}" />
                    <t:form-property-output path="email" property="${user}" />
                </jsp:body>
            </t:form-fieldset>
        </div>
        <div class="form-horizontal">
            <t:form-fieldset legendKey="form.profile.entity.legend">
                <jsp:attribute name="extension">
                    <t:accordion id="entity_accordion">
                        <t:accordion-panel parentId="entity_accordion" id="users_accordion_panel">
                            <jsp:attribute name="title">
                                Other users of ${user.entity.name}
                            </jsp:attribute>
                            <jsp:body>
                                <jsp:include page="entity_users_table.jsp" />
                            </jsp:body>
                        </t:accordion-panel>
                        <sec:authorize ifAllGranted="ROLE_ADMIN">
                            <t:accordion-panel parentId="entity_accordion" id="tokens_accordion_panel">
                                <jsp:attribute name="title">Outgoing invitations to join entity</jsp:attribute>
                                <jsp:body>
                                    <jsp:include page="entity_tokens_table.jsp" />
                                </jsp:body>
                            </t:accordion-panel>
                        </sec:authorize>
                    </t:accordion>
                </jsp:attribute>
                <jsp:attribute name="formActions">
                    <c:if test="${user.admin}">
                        <a id="disable_entity" class="btn btn-default"><fmt:message key="form.profile.action.disable_entity" /></a>
                    </c:if>
                </jsp:attribute>
                <jsp:body>
                    <t:form-property-output path="name" property="${user.entity}" />
                </jsp:body>
            </t:form-fieldset>
        </div>
        <!-- TODO add help text so user knows what the action implies -->


        <a id="btn_reload_page" href="<c:url value="/budgetanalyzer/user/profile" />">Reload page</a>
    </jsp:body>
</t:template>