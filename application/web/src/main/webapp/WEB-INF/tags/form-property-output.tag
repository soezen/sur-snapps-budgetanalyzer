<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@tag description="Form output of a property field with label" pageEncoding="UTF-8"%>
<%@attribute name="path" fragment="false" required="true" %>
<%@attribute name="property" type="java.lang.Object" fragment="false" required="true" %>

<div class="form-group">
    <label for="${path}" class="col-sm-3 control-label">
        <fmt:message key="${fn:toLowerCase(property.class.simpleName)}.${path}" />
    </label>
    <div class="col-sm-9">
        <p id="${path}" class="form-control-static">
            <c:out value="${property[path]}" />
        </p>
    </div>
</div>