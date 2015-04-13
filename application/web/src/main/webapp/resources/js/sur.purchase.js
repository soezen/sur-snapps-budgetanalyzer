var sur = sur || {};
sur.purchase = (function() {

    var productsTable = new sur.table.Table('products');
    var paymentsTable = new sur.table.Table('payments');
    var findProductByCategoryAndTypePopup = $('#findProductByCategoryAndTypePopup');

    function openFindProductByCategoryAndTypePopup() {
        var codeInput = $("#code");
        findProductByCategoryAndTypePopup.modal()
            .on('hidden.bs.modal', function () {
                codeInput.val('');
                codeInput.focus();
            });
        $.get(sur.baseUrl + 'products/search', function (response) {
            findProductByCategoryAndTypePopup.find('.modal-body').html(response);
        });
    }

    function findProductByCode() {
        var storeId = $("#storeId").val();
        var data = {
            productCode: $("#code").val(),
            storeId: storeId
        };
        $.getJSON(sur.url('/products/findProductByCode'), data, function (response) {
            if (response.success) {
                var product = response.value['product'];
                selectProduct(product['id'], product['name']);
            } else {
                openFindProductByCategoryAndTypePopup();
            }
        });
        // TODO add euro sign in column of unit price
        // TODO add unit description in amount column (depending on selected product)
        // TODO when not logged in and straight to purchase page, we see content -> should redirect to login screen
    }
    // TODO add hidden input for value
    // TODO drop down only with display value (put value in hidden via javascript)
    // TODO or option.value = display value and actual value is in data attribute (only possible for store)

    function findStoreLocations(storeId) {
        $.getJSON(sur.url('/stores/findStoreLocations'), {storeId: storeId.value}, function(response) {
            var storeLocationList = $("#storeLocationId-list");
            storeLocationList.empty();
            $("#storeLocationId_input").val('');

            var $code = $("#code");
            var $btnFindProductByCode = $("#btn_find_product_by_code");
            var $noStoreSelectedWarning = $("#no_store_selected_warning");

            $code.attr('disabled', 'true');
            $btnFindProductByCode.attr('disabled', 'true');
            $noStoreSelectedWarning.show();

            if (response.success) {
                if (response.value.length > 0) {
                    $code.removeAttr('disabled');
                    $btnFindProductByCode.removeAttr('disabled');
                    $noStoreSelectedWarning.hide();
                }
                response.value.forEach(function(item) {
                    var option = document.createElement("option");
                    option.value = item.displayValue;
                    option.dataset['value'] = item.id;
                    storeLocationList.append(option);
                });
            }
        });
    }

    function selectProduct(productId, productDescription) {
        var codeInput = $("#code");
        var rowIndex = productsTable.rowIndexOf(codeInput);
        productsTable.copyRow(rowIndex);

        var removeButton = $(productsTable.cell('remove', rowIndex));
        removeButton.find('i').removeClass('hidden');

        var description = $(productsTable.cell('description', rowIndex));
        description.find('span').text(productDescription);
        description.find('div.input-group').remove();
        description.find('input[name$=id]').val(productId);
        $("#code").val('');

        var amount = $(productsTable.cell('amount', rowIndex)).find('input');
        amount.removeAttr('disabled');
        amount.removeAttr('readonly');

        var unitPrice = $(productsTable.cell('unitPrice', rowIndex)).find('input');
        unitPrice.removeAttr('disabled');
        unitPrice.removeAttr('readonly');
        unitPrice.focus();

        findProductByCategoryAndTypePopup.modal('hide');
    }

    /**
     * Will update the total price of the product and
     * if this is last row in table, a new empty row is added.
     *
     * @param element an element within the row of finished product
     */
    function updateRowTotal(element) {
        var rowIndex = productsTable.rowIndexOf(element);
        var unitPrice = Number($(productsTable.cell('unitPrice', rowIndex)).find('input[type="number"]').val());
        var amount = Number($(productsTable.cell('amount', rowIndex)).find('input[type="number"]').val());
        var totalPrice = unitPrice * amount;
        totalPrice = totalPrice.toFixed(2);
        // TODO format currency
        $(productsTable.cell('totalPrice', rowIndex)).find('span.euro').text(totalPrice);
        calculateTotalAmount();
    }

    function removeRow(element) {
        var rowIndex = productsTable.rowIndexOf(element);
        productsTable.removeRow(rowIndex);

        calculateTotalAmount();
    }

    function calculateTotalAmount() {
        var rowCount = productsTable.rowCount();
        var total = 0;
        for (var i = 0; i < rowCount; i++) {
            var rowTotal = Number($(productsTable.cell('totalPrice', i)).find('span.euro').text());
            total += rowTotal;
        }
        total = total.toFixed(2);

        $("#total-amount").text(total);
        return total;
    }

    // TODO-TECH write visitor method on rows: forEachRow(function(row) {})
    function calculateCoveredAmount() {
        var rowCount = paymentsTable.rowCount();
        var total = 0;
        for (var i = 0; i < rowCount; i++) {
            var rowTotal = Number($(paymentsTable.cell('amount', i)).find('input').val());
            total += rowTotal;
        }
        return total;
    }

    function fillInUncoveredAmount(input) {
        if (input.value != '') {
            return;
        }
        input.value = calculateTotalAmount() - calculateCoveredAmount();
    }

    return {
        findProductByCode: findProductByCode,
        findStoreLocations: findStoreLocations,
        findProductByCategoryAndType: openFindProductByCategoryAndTypePopup,
        selectProduct: selectProduct,
        updateRowTotal: updateRowTotal,
        removeRow: removeRow,
        fillInUncoveredAmount: fillInUncoveredAmount
    };
})();

// TODO focus date when entering purchase page
// TODO use correct tab order on every page