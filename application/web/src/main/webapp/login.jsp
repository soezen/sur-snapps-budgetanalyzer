<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template>
    <jsp:attribute name="title">Login</jsp:attribute>
    <jsp:body>
        <c:if test="${!empty param.login_error}">
            <div style="color: red; margin-bottom:1em; font-size:large;"> Incorrect username and/or password </div>
        </c:if>

        <form action="<c:url value='/j_spring_security_check'/>" method="post">
            <table>
                <tr>
                    <td>Username:</td>
                    <td><input type="text" name="j_username" id="username" /></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="j_password" id="password" /></td>
                </tr>
                <tr>
                    <td colspan='2'><input id="btn_login" name="submit" type="submit" value="Login"/></td>
                </tr>
            </table>
        </form>

        <p>If you do not have a user account yet, please register
            <a href="budgetanalyzer/userRegistration.htm" id="btn_open_registration_page">here</a>.
        </p>

    </jsp:body>
</t:template>

