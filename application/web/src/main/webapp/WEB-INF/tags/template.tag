<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        <c:if test="${not empty success}">
            <fmt:message key="${success_message}" />
        </c:if>
        <c:if test="${not empty errors}">
            <ul>
                <c:forEach var="error" items="${errors}">
                    <li><fmt:message key="${error}" /></li>
                </c:forEach>
            </ul>
        </c:if>

        <s:authorize ifAllGranted="ROLE_USER">
            <s:authentication property="principal" var="principal" scope="page" />
            <!-- TODO try to get name instead of username -->
            <p>Logged in as ${principal.username} (<a href="<c:url value="/j_spring_security_logout"/>">Logout</a>)</p>
        </s:authorize>

    <jsp:doBody/>
    </body>
</html>