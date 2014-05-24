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
        <c:if test="${empty success}">
            <!-- TODO send verification email or confirmation email? -->
            <f:form modelAttribute="user" action="postUserRegistration" method="post">
                    <f:errors cssClass="form_error" />
                    <f:hidden path="tokenValue" />
                    <c:set var="withToken" value="${not empty user.tokenValue}" />
                    <table>
                        <tr>
                            <td>
                                <f:label path="name">Name</f:label>
                            </td>
                            <td>
                                <f:input path="name" />
                                <br />
                                <!-- TODO template for error so cssStyle is not needed everytime -->
                                <f:errors path="name" cssClass="field_error" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <f:label path="username">Login</f:label>
                            </td>
                            <td>
                                <f:input path="username" />
                                <br />
                                <f:errors path="username" cssClass="field_error" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <f:label path="email">Email</f:label>
                            </td>
                            <td>
                                <f:input path="email" type="email" readonly="${withToken}" />
                                <br />
                                <f:errors path="email" cssClass="field_error" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <f:label path="password">Password</f:label>
                            </td>
                            <td>
                                <f:password path="password" />
                                <br />
                                <f:errors path="password" cssClass="field_error" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a id="btn_cancel" href="<c:url value="/"/>">Cancel</a>
                            </td>
                            <td>
                                <input id="btn_register" type="submit" value="Register" />
                            </td>
                        </tr>
                    </table>
                </f:form>
        </c:if>
    </jsp:body>
</t:template>