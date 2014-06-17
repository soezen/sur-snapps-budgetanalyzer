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
        <td data-type="${isDate ? 'date' : (isEmail ? 'email' : '')}">
            <c:choose>
                <c:when test="${isDate}">
                    <fmt:formatDate value="${sur_property}" pattern="dd-MM-yyyy" />
                </c:when>
                <c:when test="${isTimestamp}">
                    <fmt:formatDate value="${sur_property}" pattern="dd-MM-yyyy HH:mm" />
                </c:when>
                <c:when test="${className eq 'java.lang.Boolean'}">
                    <sur:check-icon value="${sur_property}" />
                </c:when>
                <c:when test="${isEmail}">
                    <a href="<c:url value="mailto:${sur_property.address}" />">
                        <i class="fa fa-envelope-o"></i>
                        <span><c:out value="${sur_property.address}" /></span>
                    </a>
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