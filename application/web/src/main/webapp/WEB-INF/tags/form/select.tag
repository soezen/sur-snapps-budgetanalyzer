<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Form input field with label" pageEncoding="UTF-8" dynamic-attributes="attributes" %>
<%@attribute name="path" fragment="false" required="true" %>
<%@attribute name="labelKey" fragment="false" %>

<c:set var="width" value="12" />
<div class="form-group">
    <c:if test="${not empty labelKey}">
        <c:set var="width" value="9" />
        <label for="${path}_input" class="col-sm-3 control-label">
            <fmt:message key="${labelKey}" />
        </label>
    </c:if>
    <div class="col-sm-${width}" data-sur-type="select">
        <input id="${path}" name="${path}" type="hidden" onchange="${attributes.onchange}"  title="select"/>
        <input id="${path}_input" list="${path}-list" class="form-control" tabindex="${attributes.tabindex}" />
        <datalist id="${path}-list">
            <jsp:doBody />
        </datalist>
    </div>
</div>