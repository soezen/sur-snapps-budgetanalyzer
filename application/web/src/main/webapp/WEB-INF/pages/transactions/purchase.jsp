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
                    <sur-form:select path="storeId" labelKey="purchase.storeId" onchange="sur.purchase.findStoreLocations(this);">
                        <c:forEach items="${stores}" var="store">
                            <option data-value="${store.id}" value="${store.displayValue}"></option>
                        </c:forEach>
                    </sur-form:select>
                    <!-- TODO add item 'new store' when selecting that item, popup with form to create new store -->
                    <!-- after store is created, it is default selected -->
                    <sur-form:select path="storeLocationId" labelKey="purchase.storeLocationId" />
                </jsp:body>
            </sur-form:form-fieldset>

            <!-- TODO payment table with checkbox when checked amount is disabled and entered with rest of unpaid amount -->
            <!-- when pressing tab after checking that checkbox, we get to submit button -->
            <!-- TODO show counters of total amount of purchase, and total amount already covered by registered payments -->
            <!-- TODO filter select list of accounts with accounts already selected -->
            <!-- when no more accounts available do not create a new row -->

            <!-- TODO this should not be in form -->
            <!-- but when do we save the amounts then? -->
            <!-- put ajax on both input fields? or see if it is possible to put whole table in form -->
            <!-- TODO input fields amount and unit price not editable when there is no product selected -->
            <sur:table id="products" columns="remove,code,description,unitPrice,amount,totalPrice">
                <jsp:attribute name="footerRows">
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><span id="total-amount" class="euro">0</span></td>
                    </tr>
                </jsp:attribute>
                <jsp:body>
                    <tr>
                        <td><i class="fa fa-trash-o fa-lg btn-small-icon hidden" onclick="sur.purchase.removeRow(this);"></i></td>
                        <td colspan="2">
                            <input type="hidden" name="products[0].id" />
                            <input type="text" id="code" onkeydown="sur.purchase.findProductByCode(event, this);" class="form-control" />
                            <span ></span>
                        </td>
                        <td class="hidden"></td>
                        <td>
                            <input type="number" name="products[0].unitPrice" onblur="sur.purchase.updateRowTotal(this)" readonly disabled class="form-control euro" step="any" />
                        </td>
                        <td>
                            <input type="number" name="products[0].amount" onblur="sur.purchase.updateRowTotal(this)" readonly disabled class="form-control" step="any" />
                        </td>
                        <td><span class="euro">0</span></td>
                    </tr>
                </jsp:body>
            </sur:table>
            <sur:table id="payments" columns="account,amount">
                <tr>
                    <td>
                        <sur-form:select path="payments[0].accountId" tabindex="20">
                            <c:forEach items="${accounts}" var="account">
                                <option data-value="${account.id}" value="${account.displayValue}"></option>
                            </c:forEach>
                        </sur-form:select>
                    </td>
                    <td>
                        <input type="number" name="payments[0].amount" class="form-control" onfocus="sur.purchase.fillInUncoveredAmount(this)" step="any" />
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
