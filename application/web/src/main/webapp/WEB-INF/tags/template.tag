<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>

<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" href="<c:url value="/styles/application.css" />" type="text/css"/>
        <title>BudgetAnalyzer - <jsp:invoke fragment="title" /></title>
    </head>

    <body>
    <div id="main_wrapper">
        <h1><jsp:invoke fragment="title" /></h1>
        <s:authentication property="principal" var="principal" scope="page" />
        <c:if test="${principal != 'anonymousUser'}">
            <p>Logged in as ${principal.username} (<a href="<c:url value="/j_spring_security_logout"/>">Logout</a>)</p>
        </c:if>

    <jsp:doBody/>
    </body>
</html>