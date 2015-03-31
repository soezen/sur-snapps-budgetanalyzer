<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag description="Form input field with label" pageEncoding="UTF-8" dynamic-attributes="attributes" %>
<%@attribute name="id" fragment="false" required="true" %>
<%@attribute name="labelKey" fragment="false" %>

<div class="form-group">
    <!-- TODO do not include this if labelKey is empty -->
    <label for="${id}_input" class="col-sm-3 control-label">
        <fmt:message key="${labelKey}" />
    </label>
    <div class="col-sm-9" data-sur-type="select">
        <input id="${id}" name="${id}" type="hidden" onchange="${attributes.onchange}"  title="select"/>
        <input id="${id}_input" list="${id}-list" class="form-control" />
        <datalist id="${id}-list">
            <jsp:doBody />
        </datalist>
    </div>
</div>