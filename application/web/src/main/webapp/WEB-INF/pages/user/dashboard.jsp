<%--
  User: Soezen
  Date: 6/04/14
  Time: 10:02
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <jsp:attribute name="title">Dashboard</jsp:attribute>
    <jsp:body>
        <ul>
            <!-- TODO other link name for non-admin users -->
            <!-- TODO use message properties for text -->
            <li><a id="btn_manage_users" href="<c:url value="/budgetanalyzer/user/manageUsers" />">Manage Users</a></li>
        </ul>
    </jsp:body>
</t:template>