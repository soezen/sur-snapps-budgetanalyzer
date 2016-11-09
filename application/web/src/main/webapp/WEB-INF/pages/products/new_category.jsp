<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>

<div class="form-horizontal">
    <f:form modelAttribute="category" action="postNewCategory" method="post" onsubmit="sur.products.postNewCategory(event, this)">
        <sur-form:form-fieldset>
            <jsp:attribute name="formActions">
                <input id="btn_create_category" class="btn btn-primary" type="submit" value="<fmt:message key="form.action.submit" />" />
                <%--<a id="btn_cancel" class="btn btn-default" href="<c:url value="/budgetanalyzer/transactions/overview" />">--%>
                    <%--<fmt:message key="form.action.cancel" />--%>
                <%--</a>--%>
            </jsp:attribute>
            <jsp:body>
                <sur-form:form-property-input path="parent" property="category" type="hidden" />
                <sur-form:form-property-input path="name" property="category" />
            </jsp:body>
        </sur-form:form-fieldset>
    </f:form>
</div>