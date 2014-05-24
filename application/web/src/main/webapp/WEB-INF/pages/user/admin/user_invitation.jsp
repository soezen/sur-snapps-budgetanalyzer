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

<!-- Scenario: -->
<!-- 1. user create account -->
<!-- -> entity is created for that user -->
<!-- 2. user invites other user to have access to that entity -->
<!-- -> mail is sent to invitee -->
<!-- 3. user accepts invitation -->
<!-- -> user is created and linked to existing entity -->
<!-- : nice to have or not? -->
<!-- -> if user already has an account: give user option to not create a new user but join his existing entity with the other one: who will be admin then, -->
<!-- -> this will also mean that other user now also has access to new users accounts -->

<!-- TODO form template with java classes -->
<t:template>
    <jsp:attribute name="title">Invite User</jsp:attribute>
    <jsp:body>
        <f:form modelAttribute="user" action="postInviteUser" method="post">
            <f:errors />

            <!-- TODO create form template -->

            <table>
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
                        <a href="<c:url value="/budgetanalyzer/user/manageUsers"/>">Cancel</a>
                    </td>
                    <td>
                        <input type="submit" value="Invite" />
                    </td>
                </tr>
            </table>
        </f:form>
    </jsp:body>
</t:template>