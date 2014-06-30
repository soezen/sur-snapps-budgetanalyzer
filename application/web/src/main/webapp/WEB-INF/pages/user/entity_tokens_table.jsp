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

<a href="<c:url value="/budgetanalyzer/user/admin/inviteUser" />">
    <fmt:message key="form.profile.action.invite" />
</a>
<t:table id="tokens" columns="email,expirationDate,status" actionColumn="true">
    <c:forEach var="token" items="${tokens}">
        <t:row value="${token}">
            <td>
                <a name="resend_invitation" data-visible="in(column(status),[VALID])"
                   onclick="sur.updateRow(this, '<c:url value="/budgetanalyzer/user/admin/invitation_action/${token.id}" />', 'resend');">
                    <fmt:message key="form.profile.action.resend" />
                </a>
                <a name="extend_invitation" data-visible="equal(column(status),VALID)"
                   onclick="sur.updateRow(this, '<c:url value="/budgetanalyzer/user/admin/invitation_action/${token.id}" />', 'extend');">
                    <fmt:message key="form.profile.action.extend" />
                </a>
                <a name="revoke_invitation" data-visible="equal(column(status),VALID)"
                   onclick="sur.updateRow(this, '<c:url value="/budgetanalyzer/user/admin/invitation_action/${token.id}" />', 'revoke');">
                    <fmt:message key="form.profile.action.revoke" />
                </a>
                <a name="restore_invitation" data-visible="in(column(status),[REVOKED,EXPIRED])"
                   onclick="sur.updateRow(this, '<c:url value="/budgetanalyzer/user/admin/invitation_action/${token.id}" />', 'restore');">
                    <fmt:message key="form.profile.action.restore" />
                </a>
            </td>
        </t:row>
    </c:forEach>
</t:table>

