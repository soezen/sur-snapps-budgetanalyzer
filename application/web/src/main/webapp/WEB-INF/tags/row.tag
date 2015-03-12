<%@tag import="com.google.common.base.CaseFormat" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@tag description="Table" pageEncoding="UTF-8"%>
<%@attribute name="value" fragment="false" type="java.lang.Object" %>

<tr>
    <c:forEach var="column" items="${columns}">
        <sur:set-property-value path="${column}" property="${value}" />
        <c:set var="className" value="${sur_property.class.name}" />
        <c:set var="isDate" value="${className eq 'java.util.Date'}" />
        <c:set var="isEmail" value="${className eq 'sur.snapps.budgetanalyzer.domain.user.Email'}" />
        <c:set var="isTimestamp" value="${className eq 'java.sql.Timestamp'}" />
        <c:set var="isEnum" value="${sur_property.class.enum}" />
        <c:set var="isBoolean" value="${className eq 'java.lang.Boolean'}" />
        <c:set var="iconClass" value="data-icon-class='${fn:toLowerCase(sur_property.class.simpleName)}'" />
        <td data-type="${isDate ? 'date' : (isEmail ? 'email' : (isEnum or isBoolean ? 'icon' : ''))}" ${isEnum or isBoolean ? iconClass : ''}>
            <c:choose>
                <c:when test="${isDate}">
                    <fmt:formatDate value="${sur_property}" pattern="dd-MM-yyyy" />
                </c:when>
                <c:when test="${isTimestamp}">
                    <fmt:formatDate value="${sur_property}" pattern="dd-MM-yyyy HH:mm" />
                </c:when>
                <c:when test="${className eq 'java.lang.Boolean'}">
                    <i class="fa boolean-${fn:toLowerCase(sur_property)}"></i>
                </c:when>
                <c:when test="${isEmail}">
                    <a href="<c:url value="mailto:${sur_property.address}" />">
                        <i class="fa fa-envelope-o"></i>
                        <span><c:out value="${sur_property.address}" /></span>
                    </a>
                </c:when>
                <c:when test="${isEnum}">
                    <i class="fa fa-lg ${fn:toLowerCase(sur_property.class.simpleName)}-${fn:toLowerCase(sur_property)}"></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${sur_property}" />
                </c:otherwise>
            </c:choose>
        </td>
    </c:forEach>
    <c:if test="${actionColumn}">
        <jsp:doBody />
    </c:if>
</tr>