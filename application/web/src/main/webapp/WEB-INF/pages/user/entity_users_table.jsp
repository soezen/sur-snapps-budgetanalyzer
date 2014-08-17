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
                    <!-- TODO-FUNC UC-1 order by timestamp desc -->
                    <c:if test="${user.enabled}">
                        <s:authorize ifAllGranted="ROLE_ADMIN">
                            <a name="transfer_admin_role"
                               onclick="sur.updateRow(this, '<c:url value="/budgetanalyzer/user/admin/transfer_admin_role/${user.id}" />');"
                               title="<fmt:message key="form.profile.action.transfer_admin_role" />">
                                <i class="fa fa-lg fa-hand-o-right"></i>
                            </a>
                            <a name="disable_user"
                               onclick="sur.updateRow(this, '<c:url value="/budgetanalyzer/user/admin/disable_user/${user.id}" />')"
                               title="<fmt:message key="form.profile.action.close_user" />">
                                <i class="fa fa-lg fa-lock"></i>
                            </a>
                        </s:authorize>
                    </c:if>
                </td>
            </sur:row>
        </c:if>
    </c:forEach>
</sur:table>