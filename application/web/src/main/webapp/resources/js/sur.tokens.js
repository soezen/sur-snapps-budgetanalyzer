/**
 * Created by sur on 23/03/2015.
 */

var sur = sur || {};
sur.tokens = (function() {

    function updateTokenRow(clickedLink, url, action) {
        $(clickedLink).closest("tr").addClass("updating");
        $(clickedLink).closest("td").find("i").addClass("hidden");
        $(clickedLink).closest("td").find("i.fa-refresh").removeClass("hidden");
        $.getJSON(url, { action : action }, function(response) {
            new sur.form.Response(response, clickedLink).show();
            var row = $(clickedLink).closest("tr");
            $(clickedLink).closest("td").find("i").removeClass("hidden");
            $(clickedLink).closest("td").find("i.fa-refresh").addClass("hidden");
            sur.table.updateRowValues(response, row);
        });
    }

    return {
        updateTokenRow: updateTokenRow
    };
})();


// TODO correct changing of password not ok yet: shows the new password in plain text (but encrypted)
// TODO also translate field names



// TODO-BUG when editing field, first with validation error and then with success: validation error message still visible (same with success message)

