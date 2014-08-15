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
            sur.showResponseMessage(response);
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


    // TODO correct changing of password not ok yet: shows the new password in plain text (but encrypted)
    // TODO also translate field names
    // TODO do not copy value if field type = password

    sur.submit = function(editGroup, url) {

        var values = {};
        var password = false;
        $("div[data-edit-group='" + editGroup + "']").find("input").each(function() {
            values[this.id] = this.value;
            password = this.type == 'password';
        });

        $.getJSON(url, values, function(response) {
            sur.showResponseMessage(response);
            if (response.success && !password) {
                $("div[data-edit-group-readonly='" + editGroup + "']")
                    .find("span.edit-group-value")
                    .text(getTextFromResponse(response, editGroup));
            }
            sur.cancel(editGroup);
        });
    };

    function getTextFromResponse(response, editGroup) {
        var path = getPathFromEditGroup(editGroup);
        var result = response.value;
        while (path.indexOf('.') != -1) {
            var subPath = path.substring(0, path.indexOf('.'));
            path = path.substring(path.indexOf('.') + 1);
            result = result[subPath];
        }

        return result[path];
    }

    function getPathFromEditGroup(editGroup) {
        return editGroup.substring(editGroup.lastIndexOf('-') + 1);
    }

    sur.showResponseMessage = function(response) {
        $("div#form_response").remove();
        var div = $(document.createElement("div"));
        div.attr('id', 'form_response');

        if (response.success) {
            div.addClass("alert-success");
            // TODO replace with more specific message?
            div.text('Action executed with success');

            window.setTimeout(function() {
                $("#form_response").fadeTo(500, 0).slideUp(500, function(){
                    $(this).remove();
                });
            }, 10000);
        } else {
            div.addClass("alert-danger");
            div.html(response.message);
        }
        div.prepend(createCloseButton());
        div.addClass("alert");
        div.alert();
        $("#content-container").find("div.wrapper").prepend(div);
    };

    function createCloseButton() {
        var closeBtn = $(document.createElement("button"));
        closeBtn.addClass("close");
        closeBtn.attr("data-dismiss", "alert");

        var spanTimes = $(document.createElement("span"));
        spanTimes.attr("aria-hidden", "true");
        spanTimes.text('\u00D7');
        closeBtn.append(spanTimes);

        var spanClose = $(document.createElement("span"));
        spanClose.addClass("sr-only");
        spanClose.text("Close");
        closeBtn.append(spanClose);

        return closeBtn;
    }

    sur.cancel = function(editGroup) {
        if (editGroup == null) {
            return;
        }

        var editGroupElements = $("[data-edit-group='" + editGroup + "']");
        var path = getPathFromEditGroup(editGroup);
        editGroupElements.find("input.edit-group").each(function() {
            this.value = "";
        });
        var first = editGroupElements.find("input.edit-group").first()[0];
        if (first.type !== 'password') {
            first.value = getReadOnlyValue(editGroup);
        }
        editGroupElements.hide();
        $("[data-edit-group-readonly='" + editGroup + "']").show();
        sur.editing = null;
    };

    function getReadOnlyValue(editGroup) {
        return $("[data-edit-group-readonly='" + editGroup + "']")
            .find("span.edit-group-value")
            .text().trim();
    }

    sur.formId = function(element) {
        return $(element).parents("form").attr("id");
    };

    $("body").on("click", function(event) {
        if (event.target.tagName !== 'INPUT') {
            sur.cancel(sur.editing);
        }
    });

    // TODO make bug filter
    // TODO-BUG when editing field, first with validation error and then with success: validation error message still visible (same with success message)

    // show hidden form-groups with error and hide the corresponding read only form-groups
    $("div.form-group.has-error[data-edit-group]:not(:visible)").each(function() {
        $(this).show();
        $("div.form-group[data-edit-group-readonly='" + $(this).data('edit-group') + "']:visible").hide();
    });
});
