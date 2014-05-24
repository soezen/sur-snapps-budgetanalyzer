<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:template>
    <jsp:attribute name="title">User Registration</jsp:attribute>
    <jsp:body>
        <c:if test="${tokenStatus.expired}">
            <!-- TODO text depends on token.type -->
            User invitation has expired.
        </c:if>
        <c:if test="${tokenStatus.revoked}">
            User invitation has been revoked.
        </c:if>
    </jsp:body>
</t:template>