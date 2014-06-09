<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<t:template>
    <jsp:attribute name="title"><fmt:message key="title.login" /></jsp:attribute>
    <jsp:body>
        <div class="alert alert-info">
            <fmt:message key="form.login.info.no_account" />
        </div>

        <form action="<c:url value='/j_spring_security_check' />" method="post" class="form-horizontal">
            <t:form-fieldset legendKey="form.login.legend">
                <jsp:attribute name="formActions">
                    <a id="btn_cancel" class="btn" href="<c:url value="/" />"><fmt:message key="form.action.cancel" /></a>
                    <input id="btn_submit" class="btn btn-primary" type="submit" value="<fmt:message key="form.action.submit" />" />
                </jsp:attribute>
                <jsp:body>
                    <t:form-input path="username" property="user" />
                    <t:form-input path="password" property="user" type="password" />
                </jsp:body>
            </t:form-fieldset>
        </form>
    </jsp:body>
</t:template>

