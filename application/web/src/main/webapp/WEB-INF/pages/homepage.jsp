<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:authorize ifAllGranted="ROLE_USER">
    <c:redirect url="/budgetanalyzer/user/dashboard" />
</s:authorize>

<sur:cover>
    <h1 class="cover-heading"><fmt:message key="cover.header" /></h1>
    <p class="lead">
        <fmt:message key="cover.text" />
    </p>
    <p class="lead">
        <a id="menu_login" href="<c:url value="/budgetanalyzer/user/dashboard" />" class="btn btn-lg btn-default">
            <fmt:message key="cover.action.login" />
        </a>
    </p>
</sur:cover>