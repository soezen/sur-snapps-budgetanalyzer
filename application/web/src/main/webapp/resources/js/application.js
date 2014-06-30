/**
 * User: Soezen
 * Date: 9/06/14
 * Time: 16:56
 */

var sur = {};

$(document).ready(function() {

    $("select[data-execute-btn]").each(function () {
        $(this).on("change", function() {
            sur.updateExecuteLink(this);
        });
    });

    sur.updateExecuteLink = function(element) {
        var link = document.getElementById(element.dataset["executeBtn"]);
        if (element.selectedIndex > 0) {
            link.href = link.dataset["baseUrl"] + element.options[element.selectedIndex].value;
        } else {
            link.href = "";
        }
    };

    function findColumnIndex(tableName, columnName) {
        var header = $("#" + tableName).find("thead");
        var column = header.find("th[data-column='" + columnName + "']");
        return header.find("th").index(column);
    }

    // TODO-TECH put in different files
    sur.updateRow = function(clickedLink, url, action) {
        $.getJSON(url, { action : action }, function(response) {
            if (response.success) {
                var row = $(clickedLink).parentsUntil("tbody", "tr");
                var header = $(row).parentsUntil("table").siblings("thead");

                // TODO-BUG UC-1 boolean values are no longer check-icon
                var columns = header.find("th[data-column]");
                columns.each(function() {
                    var columnName = $(this).data("column");
                    var columnIndex = header.find("th").index(this);

                    var td = $(row.children("td")[columnIndex]);
                    var value = response.value[columnName];
                    var type = td.data("type");
                    if (type == 'date') {
                        value = $.format.date(value, "dd-MM-yyyy");
                    } else if (type == 'email') {
                        value = value.address;
                        td = td.find("a").find("span");
                    }
                    td.text(value);
                });

                sur.processTableVisibleItems($(row).parents("table").attr("id"));
            } else {
                var div = document.createElement("div");
                div.id = "form_error";
                $(div).addClass("alert");
                $(div).addClass("alert-danger");
                $(div).text(response.message);
                $("#content-container").find("div.wrapper").prepend(div);
            }
        });
    };

    sur.processTableVisibleItems = function(tableId) {
        $("#" + tableId).find("[data-visible]").each(function() {
            var row = $(this).parentsUntil("tbody", "tr");
            var visibleExpression = $(this).data("visible");
            var visible = applyFunction(row, visibleExpression);
            if (visible) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    };

    var functions = {};
    functions.column = function(row, params) {
        if (params.length != 1) {
            throw "invalid number of arguments";
        }
        var columnIndex = findColumnIndex(row.parents("table").attr("id"), params[0]);
        return $.trim($(row.children("td")[columnIndex]).text());
    };
    functions.in = function(row, params) {
        if (params.length != 2) {
            throw "invalid number of arguments";
        }
        var searchItem = params[0];
        var list = params[1];
        return list.indexOf(searchItem) != -1;
    };
    functions.equal = function(row, params) {
        if (params.length != 2) {
            throw "invalid number of arguments";
        }
        return params[0] == params[1];
    };

    function applyFunction(row, expression) {
        var index = expression.indexOf('(');
        if (index == -1) {
            return expression;
        }
        var functionName = expression.substr(0, index);
        return functions[functionName](row, getParams(row, expression));
    }

    function getParams(row, expression) {
        var startIndex = expression.indexOf('(') + 1;
        var endIndex = expression.lastIndexOf(')');
        var paramExpression = expression.substring(startIndex, endIndex);
        var params = paramExpression.split(',');
        var returnParams = [];

        var previous = null;
        for (var index in params) {
            var param = params[index];
            if (previous != null) {
                param = previous + "," + param;
                previous = null;
            }
            if (nbrOfOccurrences(param, '(') != nbrOfOccurrences(param, ')')
                || nbrOfOccurrences(param, '[') != nbrOfOccurrences(param, ']')) {
                previous = param;
            } else {
                returnParams.push(applyFunction(row, param.trim()));
            }
        }
        return returnParams;
    }

    function nbrOfOccurrences(expression, character) {
        return expression.length - expression.replace(character, '').length;
    }

    sur.formFocus = function(formId) {
        $("#overlay").toggleClass("overlay-active");

        var form = $("#" + formId);
        var editSelector = "[data-form-focus*='edit']";
        var showSelector = "[data-form-focus*='show']";
        var showGroupSelector = "[data-form-focus*='show-group']";
        var hideSelector = "[data-form-focus*='hide']";
        form.toggleClass("form-focus");
        if (form.hasClass("form-focus")) {
            form.find(editSelector).removeAttr("readonly");
            form.find(showSelector).show();
            form.find(showGroupSelector).parents(".form-group").show();
            form.find(hideSelector).hide();
        } else {
            form.find(editSelector).attr("readonly", "readonly");
            form.find(showSelector).hide();
            form.find(showGroupSelector).parents(".form-group").hide();
            form.find(hideSelector).show();
        }
    };

    sur.showPopup = function(popupId) {
        $("#" + popupId).modal();
    };

    sur.editing = null;
    sur.edit = function(event, editGroup) {
        event = event || window.event;
        event.cancelBubble = true;
        if (event.stopPropagation) {
            event.stopPropagation();
        }

        sur.cancel(sur.editing);

        $("[data-edit-group='" + editGroup + "']").show();
        $("[data-edit-group-readonly='" + editGroup + "']").hide();
        sur.editing = editGroup;
    };

    sur.submit = function(form_id) {
        document.forms[form_id].submit();
    };

    sur.cancel = function(editGroup) {
        if (editGroup == null) {
            return;
        }
        var editGroupElements = $("[data-edit-group='" + editGroup + "']");
        editGroupElements.parents("form").each(function() {
            this.reset();
        });
        editGroupElements.hide();
        $("[data-edit-group-readonly='" + editGroup + "']").show();
        sur.editing = null;
    };

    sur.formId = function(element) {
        return $(element).parents("form").attr("id");
    };

    $("body").on("click", function(event) {
        if (event.target.tagName !== 'INPUT') {
            sur.cancel(sur.editing);
        }
    });
});
