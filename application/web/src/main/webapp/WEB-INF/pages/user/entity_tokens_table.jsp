<%--
  User: Soezen
  Date: 09-06-2014
  Time: 21:38
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<table id="tokens" class="table">
    <thead>
    <tr>
        <th><fmt:message key="token.email" /></th>
        <th><fmt:message key="token.expiration_date" /></th>
        <th><fmt:message key="token.status" /></th>
        <th><fmt:message key="column.actions" /></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="token" items="#{tokens}">
        <tr>
            <!-- TODO add default ordering and perhaps allow user to order on different columns -->
            <td><c:out value="${token.email}" /></td>
            <td><fmt:formatDate value="${token.expirationDate}" pattern="dd-MM-yyyy" /></td>
            <td><c:out value="${token.status}" /></td>
            <td>
                <c:if test="${token.status.valid}">
                    <a name="resend_invitation" href="<c:url value="/budgetanalyzer/user/admin/resendInvitation/${token.id}" />"><fmt:message key="form.profile.action.resend" /></a><br />
                    <a name="extend_invitation" href="<c:url value="/budgetanalyzer/user/admin/extendInvitation/${token.id}" />"><fmt:message key="form.profile.action.extend" /></a><br />
                    <a name="revoke_invitation" href="<c:url value="/budgetanalyzer/user/admin/revokeInvitation/${token.id}" />"><fmt:message key="form.profile.action.revoke" /></a>
                </c:if>
                <c:if test="${token.status.revoked or token.status.expired}">
                    <a name="restore_invitation" href="<c:url value="/budgetanalyzer/user/admin/restoreInvitation/${token.id}" />"><fmt:message key="form.profile.action.restore" /></a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="3">
            <a href="<c:url value="/budgetanalyzer/user/admin/inviteUser" />">Invite new user</a>
        </td>
    </tr>
    </tfoot>
</table>