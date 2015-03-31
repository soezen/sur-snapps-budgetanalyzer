<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="css" fragment="true" %>

<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-COMPATIBLE" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <meta name="description" content="">
        <meta name="author" content="sur">
        <link rel="icon" href="<c:url value="/resources/icons/favicon.ico" />">

        <title><fmt:message key="application.name" /></title>

        <link rel="stylesheet" href="<c:url value="/resources/css/font-awesome/font-awesome.min.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/bootstrap.css" />">
        <link rel="stylesheet" href="<c:url value="/resources/css/application.css" />">
        <jsp:invoke fragment="css" />

        <script src="<c:url value="/resources/js/jquery/jquery-2.1.1.min.js" />"></script>
    </head>

    <body>
        <jsp:doBody />

        <script src="<c:url value="/resources/js/jquery/jquery-dateFormat.min.js" />"></script>
        <script src="<c:url value="/resources/js/bootstrap.js" />"></script>
        <!-- TODO remove application.js -->
        <script src="<c:url value="/resources/js/sur.core.js" />"></script>
        <script src="<c:url value="/resources/js/sur.form.js" />"></script>
        <script src="<c:url value="/resources/js/sur.table.js" />"></script>
    </body>
</html>