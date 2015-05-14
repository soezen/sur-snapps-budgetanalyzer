var sur = sur || {};
sur.products = (function() {

    var cache = (function() {
        var data = {};

        function find(key, id) {
            if (!data[key]) {
                return null;
            }
            return data[key][id];
        }

        function put(key, id, items) {
            if (!data[key]) {
                data[key] = {};
            }
            data[key][id] = items;
        }

        function clear(key, id) {
            if (!data[key]) {
                return;
            }
            delete data[key][id];
        }

        return {
            find: find,
            put: put,
            clear: clear
        };
    })();
    var categoriesDiv = $("#categories");
    var categoryCrumbs = (function() {
        var div = $("#categoryCrumbs");

        function createBreadCrumb(categoryId, text) {
            return $(document.createElement('li'))
                .addClass('active')
                .attr('data-id', categoryId)
                .text(text);
        }

        function add(categoryId, element, onclickHandler) {
            var activeBreadCrumb = div.find('li.active');
            var newBreadCrumb;

            if (categoryId == null) {
                newBreadCrumb = activeBreadCrumb;
            } else {
                newBreadCrumb = createBreadCrumb(categoryId, $(element).text());
                activeBreadCrumb.after(newBreadCrumb);
                activeBreadCrumb.removeClass('active');
            }
            newBreadCrumb.on('click', function() {
                    removeNext(this);
                    onclickHandler();
                });
            return newBreadCrumb;
        }

        function removeNext(breadCrumb) {
            div.find(breadCrumb).nextAll('li:not(.permanent)').remove();
            $(breadCrumb).addClass('active');
        }

        function active() {
            return div.find('li.active').data('id');
        }

        return {
            add: add,
            active: active
        }
    })();

    function createCategoryDiv(category) {
        var span = $(document.createElement('span'))
            .text(category.displayValue);
        span.on('click', function() {
            selectCategory(category.id, span);
        });
        var categoryDiv = $(document.createElement('div'))
            .append(span);
        categoriesDiv.append(categoryDiv);
    }

    function handleSubCategories(subCategories) {
        subCategories.forEach(createCategoryDiv);
    }

    function createProductTypeDiv(productType) {
        var span = $(document.createElement('span'))
            .text(productType.displayValue);
        span.on('click', function() {
            selectProductType(productType.id, span);
        });
        var div = $(document.createElement('div'))
            .append(span);
        categoriesDiv.append(div);
    }

    function handleProductTypes(productTypes) {
        productTypes.forEach(createProductTypeDiv);
    }

    function createProductDiv(product) {
        var span = $(document.createElement('span'))
            .text(product.displayValue);
        var div = $(document.createElement('div'))
            .append(span);
        categoriesDiv.append(div);
    }

    function handleProducts(products) {
        products.forEach(createProductDiv);
    }

    function fetchCategoryData(id) {
        categoriesDiv.empty();
        fetchData({ cache: 'categories', params: {categoryId: id}}, 'findSubCategories', handleSubCategories);
        fetchData({ cache: 'product-types', params: {categoryId: id}}, 'findProductTypes', handleProductTypes);
    }

    function selectCategory(id, element) {

        categoryCrumbs.add(id, element, function() {fetchCategoryData(id);});
        fetchCategoryData(id);
    }

    function selectProductType(id, element) {
        categoryCrumbs.add(id, element).addClass('product-type');
        categoriesDiv.empty();
        fetchData({ cache: 'products', params: {productTypeId : id}}, 'findProducts', handleProducts);
    }

    function fetchData(id, url, handler) {
        var idValue = id.params[Object.keys(id.params)[0]];
        var cachedData = cache.find(id.cache, idValue);
        if (cachedData != null) {
            handler(cachedData);
        } else {
            $.getJSON(url, id.params, function(response) {
               if (response.success) {
                   cache.put(id.cache, idValue, response.value);
                   handler(response.value);
               }
            });
        }
    }

    function openNewCategoryPopup() {
        var popup = $('#newCategoryPopup');
        popup.modal();
        $.get(sur.baseUrl + 'products/openNewCategoryPopup', function (response) {
            var modalBody = popup.find('.modal-body');
            modalBody.html(response);
            modalBody.find('input#parent').val(categoryCrumbs.active());
        });
    }

    function postNewCategory(event, form) {
        var data = $(form).serializeArray().reduce(function(a, x) { a[x.name] = x.value; return a; }, {});
        $.getJSON(form.action, data, function(response) {
            if (response.success) {
                $("#newCategoryPopup").modal('hide');
                var category = categoryCrumbs.active();
                cache.clear('categories', category);
                fetchCategoryData(category);
            }
        });
        event.preventDefault();
    }

    // TODO when we select productType, plus sign is to add product
    // TODO when we click plus, we can add product type or category

    return {
        selectCategory: selectCategory,
        openNewCategoryPopup: openNewCategoryPopup,
        postNewCategory: postNewCategory
    };
})();

// TODO consider using breadcrumbs instead of one huge table
// breadcrumbs can also be dropdowns
// add search fields
// TODO add description to categories and product types

// other option: use apple like navigation
// TODO allow user to choose in settings what he wants
