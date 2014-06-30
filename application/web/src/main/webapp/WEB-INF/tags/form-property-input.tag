<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@tag description="Form input for a property field with label and errors" pageEncoding="UTF-8"%>
<%@attribute name="path" fragment="false" required="true" %>
<%@attribute name="property" fragment="false" required="true" %>
<%@attribute name="type" fragment="false" required="false" %>
<%@attribute name="readonly" fragment="false" required="false" %>
<%@attribute name="form_focus" fragment="false" %>
<%@attribute name="visible" fragment="false" %>
<%@attribute name="layout" fragment="false" %>
<%@attribute name="editable" fragment="false" %>
<%@attribute name="show_buttons" fragment="false" %>
<%@attribute name="edit_group" fragment="false" %>

<c:choose>
    <c:when test="${layout eq 'center'}">
        <c:set var="label_width" value="5" />
        <c:set var="input_width" value="7" />
    </c:when>
    <c:when test="${layout eq 'right'}">
        <c:set var="label_width" value="9" />
        <c:set var="input_width" value="3" />
    </c:when>
    <c:otherwise>
        <c:set var="label_width" value="3" />
        <c:set var="input_width" value="9" />
    </c:otherwise>
</c:choose>
<c:set var="fieldErrors">
    <f:errors path="${path}" />
</c:set>
<c:set var="fieldErrorsClass" value="${empty fieldErrors ? '' : 'has-error'}" />
<c:set var="dataEditGroup" value="" />
<c:if test="${not empty edit_group}">
    <c:set var="dataEditGroup" value="data-edit-group='${edit_group}'" />
</c:if>
<div ${dataEditGroup} class="form-group ${fieldErrorsClass} has-feedback">
    <c:if test="${type ne 'hidden'}">
        <f:label path="${path}" cssClass="col-sm-${label_width} control-label">
            <fmt:message key="${property}.${path}" />
        </f:label>
    </c:if>
    <div class="col-sm-${input_width}">
        <f:input path="${path}" cssClass="form-control ${not empty edit_group ? 'edit-group' : '' }" type="${empty type ? 'text' : type}" readonly="${readonly}" data-form-focus="${form_focus}" />
        <!-- TODO automatically focus input when first showing it -->
        <c:if test="${not empty show_buttons and show_buttons}">
            <a onclick="sur.submit(sur.formId(this))">
                <i class="fa fa-check fa-lg" style="color:green;"></i>
            </a>
            <a onclick="sur.cancel('${edit_group}')">
                <i class="fa fa-times fa-lg" style="color:red;"></i>
            </a>
        </c:if>
        <c:if test="${not empty fieldErrors}">
            <span class="glyphicon glyphicon-remove form-control-feedback"></span>
            <p class="help-block text-danger"><f:errors path="${path}" /></p>
        </c:if>
    </div>
</div>
<c:if test="${not empty visible and not visible}">
    <script>
        // TODO-FUNC UC-1 put this in js method and use in both locations
        $("#${path}").parents(".form-group").hide();
    </script>
</c:if>