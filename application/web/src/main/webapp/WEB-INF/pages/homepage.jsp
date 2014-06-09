<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<s:authorize ifAllGranted="ROLE_USER">
    <c:redirect url="/budgetanalyzer/user/dashboard" />
</s:authorize>

<t:template>
    <jsp:attribute name="title"><fmt:message key="title.welcome" /></jsp:attribute>
    <jsp:body>
        Anonymous homepage
    </jsp:body>
</t:template>
