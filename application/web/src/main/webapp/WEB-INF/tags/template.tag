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
        <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome/font-awesome.min.css" />" type="text/css" />
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" type="text/css" />
        <link rel="stylesheet" href="<c:url value="/resources/css/application.css" />" type="text/css"/>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery-dateFormat.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/application.js" />"></script>
        <title><fmt:message key="application.name" /> - <jsp:invoke fragment="title" /></title>
    </head>

    <body>
        <s:authentication property="principal" var="principal" scope="session" />
        <div id="overlay"></div>
        <div id="header-container">
            <div class="wrapper">
                <div class="title">
                    <h1><fmt:message key="application.name" /> - <jsp:invoke fragment="title" /></h1>
                </div>
            </div>
            <div class="header-lint">
                <div class="wrapper">
                    <nav>
                        <ul>
                            <s:authorize ifAllGranted="ROLE_USER">
                                <li>
                                    <a id="menu_dashboard" href="<c:url value="/budgetanalyzer/user/dashboard" />"><fmt:message key="menu.dashboard" /></a>
                                </li>
                                <li>Accounts</li>
                                <li>Budgets</li>
                                <li>Products</li>
                            </s:authorize>
                        </ul>
                    </nav>
                    <s:authorize ifAllGranted="ROLE_USER">
                        <fmt:message key="menu.logged_in.text">
                            <fmt:param value="${principal.username}" />
                        </fmt:message>
                        (<a id="menu_profile" href="<c:url value="/budgetanalyzer/user/profile" />"><fmt:message key="menu.profile" /></a>
                        |
                        <a id="menu_logout" href="<c:url value="/j_spring_security_logout"/>"><fmt:message key="menu.logout" /></a>)
                    </s:authorize>
                    <s:authorize ifNotGranted="ROLE_USER">
                        <a id="menu_login" href="<c:url value="/budgetanalyzer/login" />"><fmt:message key="menu.login" /></a>
                         |
                        <a id="menu_register" href="<c:url value="/budgetanalyzer/userRegistration" />"><fmt:message key="menu.register" /></a>
                    </s:authorize>
                </div>
                <!-- TODO add current item as attribute and put special layout on that item -->
            </div>
        </div>
        <div id="content-container">
            <div class="wrapper">
                <!-- TODO style confirmation and error messages -->
                <c:if test="${not empty success}">
                    <div id="form_success" class="alert alert-success">
                        <fmt:message key="${success_message}" />
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div id="form_error" class="alert alert-danger">
                        <fmt:message key="${error_message}" />
                        <ul>
                            <c:forEach var="error" items="${error_items}">
                                <li><fmt:message key="${error}" /></li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
                <jsp:doBody/>
            </div>
        </div>
    </body>
</html>