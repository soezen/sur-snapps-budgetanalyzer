<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<sur:dashboard>
  <jsp:attribute name="css">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/charts.css" />">
  </jsp:attribute>
  <jsp:body>
    <h1><fmt:message key="title.transactions_overview" /></h1>
    <!-- TODO add monthly overview of transactions in categories and budgets -->
    <div id="chart">
    </div>

    <script src="<c:url value="/resources/js/charts/d3.min.js" />"></script>
    <script src="<c:url value="/resources/js/charts/donut.js" />"></script>
  </jsp:body>
</sur:dashboard>
<script>
  $(document).ready(function() {
    sur.charts.donut.show('products/categories-summary-month', $('#chart').get(0));
  });
</script>
