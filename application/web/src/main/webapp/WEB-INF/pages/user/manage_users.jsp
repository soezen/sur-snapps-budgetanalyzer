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
    <jsp:attribute name="title">Manage Users</jsp:attribute>
    <jsp:body>
        <a id="btn_reload_page" href="<c:url value="/budgetanalyzer/user/manageUsers" />">Reload page</a>
        <!-- list of users + actions -->
        <p>Users connected to this account</p>
        <table id="users">
            <thead>
                <tr>
                    <th>User</th>
                    <th>Email</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:set var="index" value="0" />
                <c:forEach var="user" items="${users}">
                    <c:set var="index" value="${index + 1}" />
                    <tr id="row_${index}">
                        <td><c:out value="${user.name}" /></td>
                        <td><c:out value="${user.email}" /></td>

                        <sec:authorize ifAllGranted="ROLE_ADMIN">
                            <td>
                                <a href="<c:url value="/budgetanalyzer/user/admin/removeUser/${user.id}" />">Remove</a>
                            </td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- list of invitations + status + actions -->
        <!-- TODO stylesheet -->
        <!-- TODO put this in separate file? -->
        <sec:authorize ifAllGranted="ROLE_ADMIN">
            <p>List of users invited to join this account</p>
            <table id="tokens">
                <thead>
                    <tr>
                        <th>Email</th>
                        <th>Expiration Date</th>
                        <th>Status</th>
                        <th></th>
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
                                    <a href="<c:url value="/budgetanalyzer/user/admin/resendInvitation/${token.id}" />">Resend invitation</a><br />
                                    <a href="<c:url value="/budgetanalyzer/user/admin/extendInvitation/${token.id}" />">Extend invitation</a><br />
                                    <a href="<c:url value="/budgetanalyzer/user/admin/revokeInvitation/${token.id}" />">Revoke invitation</a>
                                </c:if>
                                <c:if test="${token.status.revoked or token.status.expired}">
                                    <a href="<c:url value="/budgetanalyzer/user/admin/restoreInvitation/${token.id}" />">Restore invitation</a>
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
        </sec:authorize>
    </jsp:body>
</t:template>