<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="css" fragment="true" %>

<sur:html>
    <jsp:attribute name="css">
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/cover.css" />">
        <jsp:invoke fragment="css" />
    </jsp:attribute>
    <jsp:body>
        <div class="site-wrapper">
            <div class="site-wrapper-inner">
                <div class="cover-container">
                    <div class="masthead clearfix">
                        <div class="inner">
                            <h3 class="masthead-brand"><fmt:message key="application.name" /></h3>
                            <nav>
                                <ul class="nav masthead-nav">
                                    <li><a id="menu_register" href="<c:url value="/budgetanalyzer/userRegistration" />"><fmt:message key="cover.action.register" /></a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    <div class="inner cover">
                        <c:if test="${not empty error}">
                            <div id="form_response" class="alert alert-danger">
                                <fmt:message key="${error_message}" />
                                <ul>
                                    <c:forEach var="error" items="${error_items}">
                                        <li><fmt:message key="${error}" /></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                        <jsp:doBody />
                    </div>
                    <div class="mastfoot">
                        <div class="inner">
                            <p><fmt:message key="cover.footer" /></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="<c:url value="/resources/js/jquery/jquery-2.1.1.min.js" />"></script>
        <script src="<c:url value="/resources/js/bootstrap.js" />"></script>
    </jsp:body>
</sur:html>
