<%--
  User: Soezen
  Date: 09-06-2014
  Time: 21:38
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<c:set var="actionColumn" value="false" />
<s:authorize ifAllGranted="ROLE_ADMIN">
    <c:set var="actionColumn" value="true" />
</s:authorize>
<sur:table id="users" columns="name,email,enabled,admin" actionColumn="${actionColumn}">
    <c:forEach var="user" items="${users}">
        <c:if test="${user.username ne principal.username}">
            <sur:row value="${user}">
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
            </sur:row>
        </c:if>
    </c:forEach>
</sur:table>