<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<sur:dashboard>
  <h1><fmt:message key="title.account_overview" /></h1>
  <sur:table id="accounts" columns="type,name,owner,balance" actionColumn="false">
    <jsp:attribute name="footerActions">

    </jsp:attribute>
    <jsp:body>
      <c:forEach var="account" items="${accounts}">
        <sur:row value="${account}" />
      </c:forEach>
    </jsp:body>
  </sur:table>
</sur:dashboard>
