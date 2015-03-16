<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<sur:template >
  <jsp:attribute name="title"><fmt:message key="title.user_invitation" /></jsp:attribute>
  <jsp:body>
    <a href="<c:url value="/budgetanalyzer/accounts/create" />">
      <i class="fa fa-lg fa-plus"></i>
    </a>
    <sur:table id="accounts" columns="name" actionColumn="false">
      <c:forEach var="account" items="${accounts}">
        <sur:row value="${account}" />
      </c:forEach>
    </sur:table>
  </jsp:body>
</sur:template>
