<%--
  User: Soezen
  Date: 09-06-2014
  Time: 21:38
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<t:table id="tokens" columns="email,expirationDate,status" actionColumn="true">
    <c:forEach var="token" items="${tokens}">
        <t:row value="${token}">
            <td>
                <i class="fa fa-lg fa-refresh fa-spin hidden"></i>
                <a name="resend_invitation" data-visible="hasClass(icon(status),tokenstatus-valid)"
                   onclick="sur.tokens.updateTokenRow(this, '<c:url value="/budgetanalyzer/user/admin/invitation_action/${token.id}" />', 'resend');"
                   title="<fmt:message key="form.profile.action.resend" />">
                    <i class="fa fa-lg fa-mail-forward"></i>
                </a>
                <a name="extend_invitation" data-visible="hasClass(icon(status),tokenstatus-valid)"
                   onclick="sur.tokens.updateTokenRow(this, '<c:url value="/budgetanalyzer/user/admin/invitation_action/${token.id}" />', 'extend');"
                   title="<fmt:message key="form.profile.action.extend" />">
                    <i class="fa fa-lg fa-history"></i>
                </a>
                <a name="revoke_invitation" data-visible="hasClass(icon(status),tokenstatus-valid)"
                   onclick="sur.tokens.updateTokenRow(this, '<c:url value="/budgetanalyzer/user/admin/invitation_action/${token.id}" />', 'revoke');"
                   title="<fmt:message key="form.profile.action.revoke" />">
                    <i class="fa fa-lg fa-lock"></i>
                </a>
                <!-- TODO use baseUrl instead of c:url -->
                <a name="restore_invitation" data-visible="hasClass(icon(status),[tokenstatus-expired,tokenstatus-revoked])"
                   onclick="sur.tokens.updateTokenRow(this, '<c:url value="/budgetanalyzer/user/admin/invitation_action/${token.id}" />', 'restore');"
                   title="<fmt:message key="form.profile.action.restore" />">
                    <i class="fa fa-lg fa-unlock"></i>
                </a>
            </td>
        </t:row>
    </c:forEach>
</t:table>

