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
    <jsp:attribute name="title">User Registration Success</jsp:attribute>
    <jsp:body>
        <c:if test="${not empty success}">
            <p>Confirmation email has been sent.</p>
            <p>
                <a id="btn_open_user_dashboard" href="<c:url value="/budgetanalyzer/user/dashboard" />">Login</a>
            </p>
        </c:if>
    </jsp:body>
</t:template>