<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>

<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" href="/web/styles/springsource.css" type="text/css"/>
        <title>BudgetAnalyzer - <jsp:invoke fragment="title" /></title>
    </head>

    <body>
    <div id="main_wrapper">
        <h1><jsp:invoke fragment="title" /></h1>
        <jsp:doBody/>
    </body>
</html>