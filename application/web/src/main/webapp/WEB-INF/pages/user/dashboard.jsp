<%--
  User: Soezen
  Date: 6/04/14
  Time: 10:02
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sur" tagdir="/WEB-INF/tags" %>

<sur:template>
    <jsp:attribute name="title"><fmt:message key="title.dashboard" /></jsp:attribute>
    <jsp:body>
        <sur:table id="events" columns="tms,user.name,type">
            <c:forEach var="event" items="${events}">
                <sur:row value="${event}" />
            </c:forEach>
        </sur:table>
    </jsp:body>
</sur:template>