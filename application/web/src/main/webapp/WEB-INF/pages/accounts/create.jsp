<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<sur:template >
  <jsp:attribute name="title"><fmt:message key="title.accounts.create" /></jsp:attribute>
  <jsp:body>
    <f:form modelAttribute="account" action="postCreateAccount" method="post">
      <div class="form-horizontal">
        <sur-form:form-fieldset legendKey="form.create_account.legend">
                    <jsp:attribute name="formActions">
                        <a id="btn_cancel" class="btn" href="<c:url value="/budgetanalyzer/accounts/overview" />">
                          <fmt:message key="form.action.cancel" />
                        </a>
                        <input id="btn_submit" class="btn btn-primary" type="submit" value="<fmt:message key="form.action.submit" />" />
                    </jsp:attribute>
          <jsp:body>
            <sur-form:form-property-input path="name" property="account" />
          </jsp:body>
        </sur-form:form-fieldset>
      </div>
    </f:form>
  </jsp:body>
</sur:template>
