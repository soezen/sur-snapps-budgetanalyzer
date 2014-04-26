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
        <a href="<c:url value="/j_spring_security_logout" />">Logout</a>
    </jsp:body>
</t:template>