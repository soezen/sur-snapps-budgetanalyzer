<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@tag description="Form input for a property field with label and errors" pageEncoding="UTF-8"%>
<%@attribute name="path" fragment="false" required="true" %>
<%@attribute name="property" fragment="false" required="true" %>
<%@attribute name="type" fragment="false" required="false" %>
<%@attribute name="readonly" fragment="false" required="false" %>

<c:set var="fieldErrors">
    <f:errors path="${path}" />
</c:set>
<c:set var="fieldErrorsClass" value="${empty fieldErrors ? '' : 'has-error'}" />
<div class="form-group ${fieldErrorsClass} has-feedback">
    <f:label path="${path}" cssClass="col-sm-3 control-label">
        <fmt:message key="${property}.${path}" />
    </f:label>
    <div class="col-sm-9">
        <f:input path="${path}" cssClass="form-control" type="${empty type ? 'text' : type}" readonly="${readonly}" />
        <c:if test="${not empty fieldErrors}">
            <span class="glyphicon glyphicon-remove form-control-feedback"></span>
            <p class="help-block text-danger"><f:errors path="${path}" /></p>
        </c:if>
    </div>
</div>