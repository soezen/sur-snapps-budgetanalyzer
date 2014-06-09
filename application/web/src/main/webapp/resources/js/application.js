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
    }

});
