var sur = sur || {};
sur.form = {};

sur.form = (function() {
    function EditGroup(name) {
        var path = name.substring(name.lastIndexOf('-') + 1);
        var element = $("[data-edit-group='" + name + "']");
        var url = sur.baseUrl + element.data('url') + '/update-' + name;
        var readOnlyElement = $("[data-edit-group-readonly='" + name + "']");
        var value = readOnlyElement.find("span.edit-group-value");

        function edit() {
            element.show();
            readOnlyElement.hide();
        }

        function submit() {
            var values = {};
            var password = false;
            element.find("input").each(function() {
                values[this.id] = this.value;
                password = this.type == 'password';
            });

            $.getJSON(url, values, function(ajaxResponse) {
                new Response(ajaxResponse, element).show();

                if (ajaxResponse.success && !password) {
                    value.text(getTextFromResponse(ajaxResponse));
                }
                cancel();
            });
        }

        function cancel() {
            var inputFields = element.find("input.edit-group");
            inputFields.each(function() {
                this.value = "";
            });
            var first = inputFields.first()[0];
            if (first.type !== 'password') {
                first.value = value.text().trim();
            }
            element.hide();
            readOnlyElement.show();
        }

        function getTextFromResponse(response) {
            var result = response.value;
            while (path.indexOf('.') != -1) {
                var subPath = path.substring(0, path.indexOf('.'));
                path = path.substring(path.indexOf('.') + 1);
                result = result[subPath];
            }

            return result[path];
        }

        return {
            edit: edit,
            submit: submit,
            cancel: cancel
        };
    }
    function Response(ajaxResponse, element) {

        function show() {
            $("div#form_response").remove();
            var div = $(document.createElement("div"));
            div.attr('id', 'form_response');

            if (ajaxResponse.success) {
                div.addClass("alert-success");

                window.setTimeout(function() {
                    $("#form_response").fadeTo(500, 0).slideUp(500, function(){
                        $(this).remove();
                    });
                }, 10000);

            } else {
                div.addClass("alert-danger");
            }
            div.html(ajaxResponse.message);
            div.prepend(createCloseButton());
            div.addClass("alert");
            div.alert();
            $(element).closest("[data-alert='catch']").prepend(div);
        }

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

        return {
            show: show
        };
    }
    function DataList(element) {
        var hiddenInput = $(element).find('input[type="hidden"]');
        var input = $(element).find('input[list]');
        var list = $(element).find('datalist');

        function update() {
            var selectedOption = findSelectedOption();
            hiddenInput.val(selectedOption.data('value'));
            hiddenInput.trigger('change');
        }

        function findSelectedOption() {
            return list.find('option[value="' + input.val() + '"]');
        }

        return {
            update: update
        }
    }

    // TODO if value not in datalist and exit input, erase value

    var currentEditGroup = null;

    function edit(event, editGroupName) {
        event = event || window.event;
        event.cancelBubble = true;
        if (event.stopPropagation) {
            event.stopPropagation();
        }

        cancelCurrentEditGroup();
        currentEditGroup = new EditGroup(editGroupName);
        currentEditGroup.edit();
    }

    function submitEditGroup(editGroupName) {
        var editGroup = new EditGroup(editGroupName);
        editGroup.submit();
    }

    function submitValue(element, url) {
        $.getJSON(url, {value: element.value}, function(ajaxResponse) {
            alert(ajaxResponse);
        });
    }

    // TODO is this submit specific for that page, make js only for that page
    function submit(url) {
        $.getJSON(url, {}, function(ajaxResponse) {
            alert(ajaxResponse.success);
        });
    }

    function cancelEditGroup(editGroupName) {
        new EditGroup(editGroupName).cancel();
    }

    function cancelCurrentEditGroup() {
        if (currentEditGroup != null) {
            currentEditGroup.cancel();
        }
    }

    $(document).ready(function() {

        $("body").on("click", function(event) {
            if (event.target.tagName !== 'INPUT') {
                sur.form.cancelCurrentEditGroup();
            }
        });

        $('div[data-sur-type="select"]').each(function() {
            var select = $(this);
            var dataList = new DataList(select);
            select.find('input[list]').on('change', function() {
                dataList.update();
            });
        });
    });

    return {
        submitEditGroup: submitEditGroup,
        submitValue: submitValue,
        submit: submit,
        edit: edit,
        cancelEditGroup: cancelEditGroup,
        cancelCurrentEditGroup: cancelCurrentEditGroup,
        Response: Response
    };

})();