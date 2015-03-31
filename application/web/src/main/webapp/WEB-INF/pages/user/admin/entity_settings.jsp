<%--
  Created by IntelliJ IDEA.
  User: Soezen
  Date: 6/04/14
  Time: 10:36
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>

<sur:dashboard>
    <h1><fmt:message key="title.entity_settings" /></h1>
    <div class="form-horizontal">
        <sur-form:form-fieldset legendKey="form.entity_settings.legend">
            <jsp:attribute name="formActions">
                <c:if test="${currentUser.admin}">
                    <a id="disable_entity" class="btn btn-default"><fmt:message key="form.entity_settings.action.disable_entity" /></a>
                </c:if>
            </jsp:attribute>
            <jsp:body>
                <sur-form:form-property name="entity" property="${currentUser.entity}" edit_property="${editEntity}" />
                <sur-form:form-edit-group path="name" />
            </jsp:body>
        </sur-form:form-fieldset>

        <sur-form:form-fieldset legendKey="form.entity_settings.users.legend">
            <c:choose>
                <c:when test="${empty users}">
                    <fmt:message key="form.entity_settings.users.empty" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="../entity_users_table.jsp" />
                </c:otherwise>
            </c:choose>
        </sur-form:form-fieldset>
        <sur-form:form-fieldset legendKey="form.entity_settings.tokens.legend">
            <c:choose>
                <c:when test="${empty tokens}">
                    <fmt:message key="form.entity_settings.tokens.empty" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="entity_tokens_table.jsp" />
                </c:otherwise>
            </c:choose>
        </sur-form:form-fieldset>
        <!-- TODO don't show empty tables -->
    </div>

    <script src="<c:url value="/resources/js/sur.tokens.js" />"></script>
    <!-- TODO-FUNC UC-1 in popup and make user confirm, add help text so user knows what the action implies -->
</sur:dashboard>