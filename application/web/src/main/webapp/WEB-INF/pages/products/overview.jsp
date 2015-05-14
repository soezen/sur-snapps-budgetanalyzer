<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<sur:dashboard>
    <h1><fmt:message key="title.product_overview" /></h1>
    <ul id="categoryCrumbs" class="breadcrumb">
        <li class="active"><fmt:message key="products.categories" /></li>
        <li class="permanent"><i class="fa fa-plus" onclick="sur.products.openNewCategoryPopup();"></i></li>
    </ul>
    <div id="categories"></div>
    <!-- TODO add panels categories, product types and products -->
    <!-- in header + button to add one -->
    <!-- delete button and edit button per child -->


  <sur:popup id="newCategoryPopup" title="popup.new_category.title">
        <jsp:attribute name="footer">
            <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="form.action.cancel" /></button>
        </jsp:attribute>
    <jsp:body>
      <i class="fa fa-spinner fa-spin"></i>
    </jsp:body>
  </sur:popup>

  <script src="<c:url value="/resources/js/sur.products.js" />"></script>
  <script>
    $(document).on('sur-ready', function() {
        sur.products.selectCategory(null, null);
    });
  </script>
</sur:dashboard>
