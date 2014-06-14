<%--
  User: Soezen
  Date: 6/04/14
  Time: 10:02
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <jsp:attribute name="title"><fmt:message key="title.dashboard" /></jsp:attribute>
    <jsp:body>
        <table class="table">
            <thead>
                <tr>
                    <th>Time</th>
                    <th>User</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="event" items="${events}">
                    <tr>
                        <td><fmt:formatDate value="${event.tms}" /></td>
                        <td>${event.user.name}</td>
                        <td>${event.type}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:template>