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
</head>
<body>
<h1>User Registration</h1>
<p>
    <form:form modelAttribute="user" action="postUserRegistration" method="post">
        <table>
            <tr>
                <td>Username</td>
                <td><form:input path="username" /></td>
                <td><form:errors path="username" /></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><form:password path="password" /></td>
                <td><form:errors path="password" /></td>
            </tr>
            <tr>
                <td colspan="3">
                    <input type="submit" value="Create" />
                </td>
            </tr>
        </table>
    </form:form>
</p>
</body>
</html>