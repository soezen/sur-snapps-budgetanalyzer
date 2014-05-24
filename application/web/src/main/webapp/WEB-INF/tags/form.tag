<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag description="Form template" pageEncoding="UTF-8"%>
<%@attribute name="modelAttribute" fragment="false" %>
<%@attribute name="action" fragment="false" %>
<%@attribute name="method" fragment="false" %>
<%@attribute name="actions" fragment="true" %>

<f:form modelAttribute="${modelAttribute}" action="${action}" method="${method}" >
    <f:errors cssClass="field_errors" />
    <table>
        <tbody>
            <jsp:doBody var="rows" />
            <c:forEach var="row" items="${rows}">
                <tr>
                    <td>
                        <c:set var="path" value="${fn:substringBefore(fn:substringAfter(row, 'id=\"'), '\"')}" />
                        <f:label path="${path}">
                            <fmt:message key="${modelAttribute}.${path}" />
                        </f:label>
                    </td>
                    <td>
                        ${row}
                        <br />
                        <f:errors path="${path}" cssClass="field_error" />
                    </td>
                </tr>
            </c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="2">
                    <jsp:invoke fragment="actions" />
                </td>
            </tr>
        </tfoot>
    </table>
</f:form>
