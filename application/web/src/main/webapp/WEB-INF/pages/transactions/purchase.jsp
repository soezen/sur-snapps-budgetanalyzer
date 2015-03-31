<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sur-form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<sur:dashboard>
    <h1><fmt:message key="title.transactions.purchase" /></h1>
    <div class="form-horizontal">
        <f:form modelAttribute="purchase" action="postPurchase" method="post">
            <sur-form:form-fieldset legendKey="form.purchase.legend" formActionsStyle="vertical">
                <jsp:attribute name="formActions">
                    <input id="btn_submit" class="btn btn-primary" type="submit" value="<fmt:message key="form.action.submit" />" />
                    <a id="btn_cancel" class="btn btn-default" href="<c:url value="/budgetanalyzer/transactions/overview" />">
                        <fmt:message key="form.action.cancel" />
                    </a>
                </jsp:attribute>
                <jsp:body>
                    <sur-form:form-property-input path="date" property="purchase" type="date" />
                    <sur-form:select id="storeId" labelKey="purchase.storeId" onchange="sur.purchase.findStoreLocations(this);">
                        <c:forEach items="${stores}" var="store">
                            <option data-value="${store.id}" value="${store.displayValue}"></option>
                        </c:forEach>
                    </sur-form:select>
                    <!-- TODO add item 'new store' when selecting that item, popup with form to create new store -->
                    <!-- after store is created, it is default selected -->
                    <sur-form:select id="storeLocationId" labelKey="purchase.storeLocationId" />
                </jsp:body>
            </sur-form:form-fieldset>

            <!-- TODO this should not be in form -->
            <!-- but when do we save the amounts then? -->
            <!-- put ajax on both input fields? or see if it is possible to put whole table in form -->
            <!-- TODO input fields amount and unit price not editable when there is no product selected -->
            <sur:table id="products" columns="code,description,unitPrice,amount,totalPrice">
                <c:forEach var="product" items="${purchase.products}">
                    <tr>
                        <td>
                            ${product.code}
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="2">
                        <input type="hidden" name="products[0].id" />
                        <input type="text" id="code" onkeydown="sur.purchase.findProductByCode(event, this);" />
                        <span ></span>
                    </td>
                    <td class="hidden"></td>
                    <td>
                        <input type="number" name="products[0].unitPrice" readonly disabled />
                    </td>
                    <td>
                        <input type="number" name="products[0].amount" onblur="sur.purchase.addProduct(this)" readonly disabled />
                    </td>
                    <td>

                    </td>
                </tr>
            </sur:table>
        </f:form>
    </div>

    <!-- TODO 1. add data in db (stores and products -->
    <!-- TODO 2. submit form with only existing products -->
    <!-- TODO 3. add payment information -->
    <!-- TODO 4. implement search of existing product by category and type -->
    <!-- TODO 5. implement create product -->

    <div id="findProductByCategoryAndTypePopup" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title"><fmt:message key="popup.find_product.title" /></h4>
                </div>
                <div class="modal-body">
                    <p><fmt:message key="popup.find_product.text" /></p>
                    <div class="form-horizontal">
                        <!-- TODO drop down in which you can type -->
                        <!-- TODO categories most common in that store on top -->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="form.action.cancel" /></button>
                    <button type="button" class="btn btn-primary">Save Changes</button>
                </div>
            </div>
        </div>
    </div>

    <script src="<c:url value="/resources/js/bootstrap.js" />"></script>
    <script src="<c:url value="/resources/js/sur.table.js" />"></script>
    <script src="<c:url value="/resources/js/sur.purchase.js" />"></script>
</sur:dashboard>
