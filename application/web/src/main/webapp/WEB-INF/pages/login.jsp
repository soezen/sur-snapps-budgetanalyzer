<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>

<sur:cover>
    <jsp:attribute name="css">
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/login.css" />">
    </jsp:attribute>
    <jsp:body>
        <h1 class="cover-heading"><fmt:message key="form.login.legend" /></h1>
        <form action="<c:url value="/j_spring_security_check" />" method="post" class="form-signin">
            <label for="username" class="sr-only"><fmt:message key="form.login.username" /></label>
            <input id="username" name="username" type="text" class="form-control" placeholder="<fmt:message key="form.login.username" />" required autofocus>

            <label for="password" class="sr-only"><fmt:message key="form.login.password" /></label>
            <input id="password" name="password" type="password" class="form-control" placeholder="<fmt:message key="form.login.password" />" required>

            <button id="btn_login" class="btn btn-lg btn-primary btn-block" type="submit">
                <fmt:message key="form.login.action.submit" />
            </button>
            <a id="btn_cancel" class="btn" href="<c:url value="/" />"><fmt:message key="form.action.cancel" /></a>
        </form>
    </jsp:body>
</sur:cover>