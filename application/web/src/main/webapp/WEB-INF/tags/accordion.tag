<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Accordion" pageEncoding="UTF-8"%>
<%@attribute name="id" fragment="false" required="true" %>

<div class="panel-group" id="${id}">
    <jsp:doBody />
</div>

<script>
    $(document).ready(function() {
        $("#${id}").find(".panel-collapse").first().addClass("in");
    });
</script>