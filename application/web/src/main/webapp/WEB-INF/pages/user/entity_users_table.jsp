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

<table id="users" class="table">
    <thead>
    <tr>
        <th><fmt:message key="user.name" /></th>
        <th><fmt:message key="user.email" /></th>
        <th><fmt:message key="user.enabled" /></th>
        <th><fmt:message key="user.admin" /></th>
        <s:authorize ifAllGranted="ROLE_ADMIN">
            <th><fmt:message key="column.actions" /></th>
        </s:authorize>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <c:if test="${user.username ne principal.username}">
            <tr>
                <td><c:out value="${user.name}" /></td>
                <td>
                    <a href="<c:url value="mailto:${user.email}" />">
                        <i class="fa fa-envelope-o"></i>
                        <c:out value="${user.email}" />
                    </a>
                </td>
                <td><t:check-icon value="${user.enabled}" /></td>
                <td><t:check-icon value="${user.admin}" /></td>
                <td>
                    <c:if test="${user.enabled}">
                        <s:authorize ifAllGranted="ROLE_ADMIN">
                            <select id="user_actions" data-execute-btn="execute_user_action">
                                <option></option>
                                <option value="transferAdminRole"><fmt:message key="form.profile.action.transfer_admin_role" /></option>
                                <option value="disableUser"><fmt:message key="form.profile.action.disable_user" /></option>
                            </select>
                            <a id="execute_user_action" data-base-url="<c:url value="/budgetanalyzer/user/admin/" />"><fmt:message key="form.profile.action.execute" /></a>
                            <!-- TODO admin cannot remove himself but can close the account instead -->
                            <!-- TODO not admin user can only close his user registration -->
                        </s:authorize>
                    </c:if>
                </td>
            </tr>
        </c:if>
    </c:forEach>
    </tbody>
</table>