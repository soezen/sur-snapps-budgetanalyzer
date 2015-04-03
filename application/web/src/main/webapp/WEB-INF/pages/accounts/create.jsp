<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<sur:dashboard>
    <h1><fmt:message key="title.accounts.create" /></h1>
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
              <sur-form:select path="type" labelKey="account.type">
                  <c:forEach items="${accountTypes}" var="accountType">
                      <option data-value="${accountType.name()}" value="${accountType.name()}"></option>
                  </c:forEach>
              </sur-form:select>
          </jsp:body>
        </sur-form:form-fieldset>
      </div>
    </f:form>
</sur:dashboard>
