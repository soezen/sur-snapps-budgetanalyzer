<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>

<p><fmt:message key="popup.find_product.text" /></p>
<sur:table id="products" columns="name">
    <%--@elvariable id="products" type="java.util.List"--%>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <span><c:out value="${product.displayValue}" /></span>
            </td>
            <td>
                <i class="fa fa-lg fa-external-link-square btn-small-icon" onclick="sur.purchase.selectProduct('${product.id}', '${product.displayValue}');"></i>
            </td>
        </tr>
    </c:forEach>
</sur:table>