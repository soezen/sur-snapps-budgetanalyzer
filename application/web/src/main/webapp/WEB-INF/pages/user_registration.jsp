<%--
  Created by IntelliJ IDEA.
  User: Soezen
  Date: 6/04/14
  Time: 10:36
--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <jsp:attribute name="title">User Registration</jsp:attribute>
    <jsp:body>
        <f:form modelAttribute="user" action="postUserRegistration" method="post">
            <table>
                <tr>
                    <td>
                        <f:label path="name">Name</f:label>
                    </td>
                    <td>
                        <f:input path="name" />
                        <br />
                        <f:errors path="name" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <f:label path="username">Login</f:label>
                    </td>
                    <td>
                        <f:input path="username" />
                        <br />
                        <f:errors path="username" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <f:label path="email">Email</f:label>
                    </td>
                    <td>
                        <f:input path="email" type="email" />
                        <br />
                        <f:errors path="email" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <f:label path="password">Password</f:label>
                    </td>
                    <td>
                        <f:password path="password" />
                        <br />
                        <f:errors path="password" />
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
        </f:form>
    </jsp:body>
</t:template>