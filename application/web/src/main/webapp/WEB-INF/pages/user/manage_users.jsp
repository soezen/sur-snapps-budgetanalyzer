<%--
  Created by IntelliJ IDEA.
  User: Soezen
  Date: 6/04/14
  Time: 10:36
--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <jsp:attribute name="title">Manage Users</jsp:attribute>
    <jsp:body>
        <!-- list of users + actions -->
        <!-- invite new users -->
        <table>
            <thead>
                <tr>
                    <th>User</th>
                    <th>Email</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td><c:out value="${user.name}" /></td>
                        <td><c:out value="${user.email}" /></td>
                        <td></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- list of invitations + status + actions -->
        <!-- TODO stylesheet -->
        <table>
            <thead>
                <tr>
                    <th>Email</th>
                    <th>Expiration Date</th>
                    <th>Status</th>
                    <th></th>
                </tr>
                <c:forEach var="token" items="#{tokens}">
                    <tr>
                        <td><c:out value="${token.email}" /></td>
                        <!-- TODO format date and enum -->
                        <td><fmt:formatDate value="${token.expirationDate}" pattern="dd-MM-yyyy" /></td>
                        <td><c:out value="${token.status}" /></td>
                        <td></td>
                    </tr>
                </c:forEach>
            </thead>
            <tfoot>
                <tr>
                    <td colspan="3">
                        <a href="<c:url value="/budgetanalyzer/user/inviteUser" />">Invite new user</a>
                    </td>
                </tr>
            </tfoot>
        </table>
    </jsp:body>
</t:template>