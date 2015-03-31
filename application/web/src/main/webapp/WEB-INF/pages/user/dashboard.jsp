<%--
  User: Soezen
  Date: 6/04/14
  Time: 10:02
--%>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<sur:dashboard>
    <h1 class="page-header"><fmt:message key="title.dashboard" /></h1>
    <div class="table-responsive">
        <sur:table id="events" columns="tms,type" actionColumn="true">
            <%--@elvariable id="events" typne="java.util.List<sur.snapps.budgetanalyzer.domain.event.EventWithSubject>"--%>
            <c:forEach var="event" items="${events}">
                <sur:row value="${event}">
                    <td>
                        <c:if test="${not empty event.subjectId}">
                            <c:set var="subjectValue" value="${event.subject.displayValue}" />
                        </c:if>
                        <fmt:message key="events.message.${fn:toLowerCase(event.type)}">
                            <fmt:param value="${event.user.name}" />
                            <fmt:param value="${event.user.entity.name}" />
                            <fmt:param value="${subjectValue}" />
                            <fmt:param value="${event.user.emailAddress}" />
                        </fmt:message>
                        <c:set var="subjectValue" value="${null}" />
                    </td>
                </sur:row>
            </c:forEach>
        </sur:table>
    </div>
    <!-- TODO next button (and previous) or a more button on the bottom
</sur:dashboard>