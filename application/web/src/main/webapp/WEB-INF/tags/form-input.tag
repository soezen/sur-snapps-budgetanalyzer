<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@tag description="Form input field with label" pageEncoding="UTF-8"%>
<%@attribute name="path" fragment="false" required="true" %>
<%@attribute name="property" fragment="false" required="true" %>
<%@attribute name="type" fragment="false" required="false" %>
<%@attribute name="readonly" fragment="false" required="false" %>

<div class="form-group">
    <label for="${path}" class="col-sm-3 control-label">
        <fmt:message key="${property}.${path}" />
    </label>
    <div class="col-sm-9">
        <input id="${path}" name="${path}" class="form-control" type="${empty type ? 'text' : type}" ${readonly ? 'readonly' : ''} />
    </div>
</div>