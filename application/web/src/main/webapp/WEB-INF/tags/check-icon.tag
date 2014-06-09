<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Check icon" pageEncoding="UTF-8"%>
<%@attribute name="value" fragment="false" %>

<c:choose>
    <c:when test="${value}">
        <i class="fa fa-check-circle-o"></i>
    </c:when>
    <c:otherwise>
        <i class="fa fa-circle-o"></i>
    </c:otherwise>
</c:choose>