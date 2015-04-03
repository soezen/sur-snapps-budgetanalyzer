<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@tag description="Table" pageEncoding="UTF-8"%>
<%@attribute name="id" fragment="false" required="true" %>
<%@attribute name="columns" fragment="false" %>
<%@attribute name="actionColumn" fragment="false" %>
<%@attribute name="footerActions" fragment="true" %>
<%@attribute name="footerRows" fragment="true" %>

<c:set var="columns" value="${fn:split(columns, ',')}" scope="request" />
<c:set var="actionColumn" value="${actionColumn}" scope="request" />
<table class="table" id="${id}">
    <thead>
        <tr>
            <c:forEach var="column" items="${columns}">
                <th data-column="${column}"><fmt:message key="${id}.column.${column}" /></th>
            </c:forEach>
            <c:if test="${actionColumn}">
                <th>Actions</th>
            </c:if>
        </tr>
    </thead>
    <tbody>
        <jsp:doBody />
    </tbody>
    <tfoot>
        <jsp:invoke fragment="footerRows" />
        <tr>
            <td colspan="${fn:length(columns) + (actionColumn ? 1 : 0)}">
                <jsp:invoke fragment="footerActions" />
                5 records (comes in as attribute)
                + pagination buttons
            </td>
        </tr>
    </tfoot>
</table>

<script>
    // TODO move this because at this point jquery is not loaded yet
    $(document).ready(function() {
        sur.table.init('${id}');
    });
</script>