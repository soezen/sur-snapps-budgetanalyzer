var sur = sur || {};

// TODO create button to hide/display menu sidebar (or responsiveness: when medium screen, replace menu items with icons)

sur.table = (function() {

    var iconClasses = {
        tokenstatus : {
            valid: 'fa-check',
            revoked: 'fa-times',
            expired: 'fa-clock-o',
            complete: 'fa-check'
        },
        eventtype : {
            user_invitation: 'fa-check'
        },
        boolean : {
            true: 'fa-check-circle-o',
            false: 'fa-circle-o'
        },
        accounttype : {
            cash: 'fa-euro',
            virtual: 'fa-credit-card',
            cheque: 'fa-money'
        }
    };

    function findColumnIndex(tableName, columnName) {
        var header = $("#" + tableName).find("thead");
        var column = header.find("th[data-column='" + columnName + "']");
        return header.find("th").index(column);
    }

    function updateRowValues(response, row) {
        row = $(row);
        row.removeClass("updating");
        if (response.success) {
            row.addClass("updated");
            window.setTimeout(function() {
                row.removeClass("updated");
            }, 1000);

            var header = $(row.parentsUntil('table')).siblings('thead');
            var columns = header.find('th[data-column]');
            columns.each(function() {
                var columnName = $(this).data('column');
                var columnIndex = header.find('th').index(this);

                var td = $(row.children('td')[columnIndex]);
                var value = response.value[columnName];
                console.log('value : ' + value);
                // TODO if value is null or undefined, do not change content of td
                var type = td.data('type');

                if (type == 'date') {
                    value = $.format.date(new Date(value), "dd-MM-yyyy");
                    td.text(value);
                } else if (type == 'email') {
                    td.find("a").find("span").text(value.address);
                } else if (type == 'icon') {
                    var iconClass = td.data('icon-class');
                    var iconClassValues = iconClasses[iconClass];
                    for (var iconClassValue in iconClassValues) {
                        if (iconClassValues.hasOwnProperty(iconClassValue)) {
                            td.find("i.fa." + iconClass + "-" + iconClassValue).removeClass(iconClass + "-" + iconClassValue + " " + iconClassValues[iconClassValue]);
                        }
                    }
                    value = value.toString();
                    td.find("i.fa").addClass(iconClass + "-" + value.toLowerCase());
                    td.find("i.fa").addClass(iconClasses[iconClass][value.toLowerCase()]);

                } else {
                    td.text(value);
                }
            });
            init($(row).parents("table").attr("id"));
        } else {
            // TODO handle error message
        }
    }

    function init(tableId) {
        var table = $("#" + tableId);
        table.find("[data-visible]").each(function() {
            var row = $(this).parentsUntil("tbody", "tr");
            var visibleExpression = $(this).data("visible");
            var visible = applyFunction(row, visibleExpression);
            if (visible) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });

        // find icon cells that need to be translated
        table.find("tbody").find("[data-type='icon']").each(function() {
            var iconClass = $(this).data('icon-class');
            var iconClassValues = iconClasses[iconClass];
            for (var value in iconClassValues) {
                if (iconClassValues.hasOwnProperty(value)) {
                    var iconClassTranslation = iconClassValues[value];
                    $(this).find("i.fa." + iconClass + "-" + value).addClass(iconClassTranslation);
                }
            }
        });
    }

    function applyFunction(row, expression) {
        if (expression.indexOf('[') == 0 && expression.lastIndexOf(']') == expression.length - 1) {
            expression = expression.substring(1, expression.length - 1);
            return expression.split(',');
        }
        // TODO if one day you add a function into an array call applyFunction for each element in the array here.

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
        if (previous != null) {
            returnParams.push(applyFunction(row, previous));
        }
        return returnParams;
    }

    function nbrOfOccurrences(expression, character) {
        return expression.length - expression.replace(character, '').length;
    }

    var functions = {};
    functions.column = function(row, params) {
        if (params.length != 1) {
            throw "invalid number of arguments";
        }
        var columnIndex = findColumnIndex(row.parents("table").attr("id"), params[0]);
        return $.trim($(row.children("td")[columnIndex]).text());
    };
    functions.icon = function(row, params) {
        if (params.length != 1) {
            throw "invalid number of arguments";
        }
        var columnIndex = findColumnIndex(row.parents("table").attr("id"), params[0]);
        return $(row.children("td")[columnIndex]).find("i");
    };
    functions.hasClass = function(row, params) {
        if (params.length != 2) {
            throw "invalid number of arguments";
        }
        var icon = params[0];
        var requiredClasses = params[1];
        if (requiredClasses instanceof Array) {
            for (var index in requiredClasses) {
                if (requiredClasses.hasOwnProperty(index)) {
                    var requiredClass = requiredClasses[index];
                    if (icon.hasClass(requiredClass)) {
                        return true;
                    }
                }
            }
            return false;
        }
        return icon.hasClass(requiredClasses);
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

    function Table(name) {
        var element = $("table#" + name);
        var header = element.find("thead");
        var body = element.find('tbody');
        var rows;
        refreshRows();

        function refreshRows() {
            rows = body.find('tr');
        }

        function cell(columnName, rowIndex) {
            var th = header.find('th[data-column="' + columnName + '"]');
            var columnIndex = header.find('th').index(th);
            return $(rows[rowIndex]).children('td')[columnIndex];
        }

        function rowIndexOf(childElement) {
            return rows.index($(childElement).closest('tr'));
        }

        function rowCount() {
            return rows.length;
        }

        // TODO see what happens when you submit and the indexes are not 0,1,2,3 but 3,5,7,...
        function removeRow(rowIndex) {
            $(rows.get(rowIndex)).remove();
            refreshRows();
        }

        function copyRow(rowIndex) {
            var newIndex = rowCount();
            var clone = $(rows.get(rowIndex)).clone();
            clone.find('input').each(function() {
                var name = this.name;
                var startIndex = name.indexOf('[') + 1;
                var endIndex = name.indexOf(']');
                if (startIndex > 0 && endIndex > startIndex) {
                    this.name = name.substring(0, startIndex) + newIndex + name.substring(endIndex, name.length);
                }
            });
            body.append(clone);
            refreshRows();

            $(cell('code', rowCount() - 1)).find('input').val('');
        }

        return {
            cell: cell,
            rowIndexOf: rowIndexOf,
            rowCount: rowCount,
            copyRow: copyRow,
            removeRow: removeRow
        };
    }

    return {
        updateRowValues: updateRowValues,
        init: init,
        Table: Table
    }
})();

// TODO currency symbol added by init method of table (and also formatting)

