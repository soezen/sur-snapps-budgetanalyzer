var sur = sur || {};
sur.purchase = (function() {

    var productsTable = new sur.table.Table('products');

    function findProductByCode(event, codeInput) {
        if (event.keyCode !== 13
            && event.keyCode !== 9) {
            return;
        }
        // TODO add a little button next to input that triggers this when clicking
        event.preventDefault();
        var rowIndex = productsTable.rowIndexOf(codeInput);
        var data = {
            productCode: codeInput.value,
            storeId: $("#storeId").val()
        };
        $.getJSON(sur.url('/products/findProductByCode'), data, function (response) {
            if (response.success) {
                productsTable.copyRow(rowIndex);

                var product = response.value['product'];

                var code = $(productsTable.cell('code', rowIndex));
                code.attr('colspan', '1');
                code.find('input#code').remove();
                code.find('input[name$=id]').val(product['id']);
                code.find('span').text(response.value['code']);

                var description = $(productsTable.cell('description', rowIndex));
                description.removeClass('hidden');
                description.text(product['name']);

                var amount = $(productsTable.cell('amount', rowIndex)).find('input');
                amount.removeAttr('disabled');
                amount.removeAttr('readonly');

                var unitPrice = $(productsTable.cell('unitPrice', rowIndex)).find('input');
                unitPrice.removeAttr('disabled');
                unitPrice.removeAttr('readonly');
                unitPrice.focus();
            }
            if (response.success) {
            } else {
                $('#findProductByCategoryAndTypePopup').modal()
                    .on('hidden.bs.modal', function() {
                        var codeInput = $(productsTable.cell('code', rowIndex)).find('input');
                        codeInput.val('');
                        codeInput.focus();
                    });
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

            if (response.success) {
                response.value.forEach(function(item) {
                    var option = document.createElement("option");
                    option.value = item.displayValue;
                    option.dataset['value'] = item.id;
                    storeLocationList.append(option);
                });
            }
        });
    }

    /**
     * Will update the total price of the product and
     * if this is last row in table, a new empty row is added.
     *
     * @param element an element within the row of finished product
     */
    function addProduct(element) {
        var rowIndex = productsTable.rowIndexOf(element);
        var unitPrice = Number($(productsTable.cell('unitPrice', rowIndex)).find('input[type="number"]').val());
        var amount = Number($(productsTable.cell('amount', rowIndex)).find('input[type="number"]').val());
        var totalPrice = unitPrice * amount;
        // TODO format currency
        $(productsTable.cell('totalPrice', rowIndex)).text(totalPrice);
    }

    return {
        findProductByCode: findProductByCode,
        findStoreLocations: findStoreLocations,
        addProduct: addProduct
    };
})();

// TODO focus date when entering purchase page
// TODO use correct tab order on every page