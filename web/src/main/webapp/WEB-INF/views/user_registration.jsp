<%--
  Created by IntelliJ IDEA.
  User: Soezen
  Date: 6/04/14
  Time: 10:36
--%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Budget Analyzer - User Registration</title>
    <!-- TODO add to resources -->
    <link rel="stylesheet" href="/web/styles/springsource.css" type="text/css"/>
</head>
<body>
    <div id="main_wrapper">
        <h1>User Registration</h1>
        <p>
            <form:form modelAttribute="user" action="postUserRegistration" method="post">
                <table>
                    <tr>
                        <td>
                            <form:label path="username">Username</form:label>
                        </td>
                        <td>
                            <form:input path="username" />
                            <br />
                            <form:errors path="username" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="password">Password</form:label>
                        </td>
                        <td>
                            <form:password path="password" />
                            <br />
                            <form:errors path="password" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a href="<c:url value="/"/>">Cancel</a>
                        </td>
                        <td>
                            <input type="submit" value="Create" />
                        </td>
                    </tr>
                </table>
            </form:form>
        </p>
    </div>
</body>
</html>