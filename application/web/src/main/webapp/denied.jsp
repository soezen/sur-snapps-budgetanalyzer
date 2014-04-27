<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template>
    <jsp:attribute name="title">Access Denied</jsp:attribute>
    <jsp:body>
        <p>Sorry, Access Denied</p>
        <p><a href="/web">Return to Home Page</a> or</p>
        <p><a href="/web/j_spring_security_logout">Logout</a></p>
    </jsp:body>
</t:template>
