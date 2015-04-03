<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@tag description="Form input for a property field with label and errors" pageEncoding="UTF-8" dynamic-attributes="attributes" %>
<%@attribute name="path" fragment="false" required="true" %>
<%@attribute name="property" fragment="false" type="java.lang.Object" %>
<%@attribute name="type" fragment="false" required="false" %>
<%@attribute name="readonly" fragment="false" required="false" %>
<%@attribute name="form_focus" fragment="false" %>
<%@attribute name="visible" fragment="false" %>
<%@attribute name="layout" fragment="false" %>
<%@attribute name="editable" fragment="false" %>
<%@attribute name="show_buttons" fragment="false" %>
<%@attribute name="edit_group" fragment="false" %>
<%@attribute name="spring_input" fragment="false" %>


<c:if test="${empty property}">
    <c:set var="property" value="${sur_readonly_property}" />
</c:if>
<c:if test="${empty edit_property}">
    <c:set var="edit_property" value="${sur_edit_property}" />
</c:if>

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
        <c:set var="input_width" value="4" />
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

<div ${dataEditGroup} class="form-group ${fieldErrorsClass} has-feedback" data-url="${fn:toLowerCase(sur_readonly_property.class.simpleName)}">
    <c:if test="${type ne 'hidden'}">
        <c:choose>
            <c:when test="${spring_input eq 'false'}">
                <label for="${path}" class="col-sm-${label_width} control-label">
                    <fmt:message key="${fn:toLowerCase(property.class.simpleName)}.${path}" />
                </label>
            </c:when>
            <c:otherwise>
                <f:label path="${path}" cssClass="col-sm-${label_width} control-label">
                    <fmt:message key="${property}.${path}" />
                </f:label>
            </c:otherwise>
        </c:choose>
    </c:if>
    <div class="col-sm-9 col-md-7 col-lg-4">
        <c:set var="readonly_attribute" value="" />
        <c:if test="${not empty readonly}">
            <c:set var="readonly_attribute" value="readonly='${readonly}'" />
        </c:if>
        <c:choose>
            <c:when test="${spring_input eq 'false'}">
                <input id="${fn:toLowerCase(property.class.simpleName)}-${path}"
                       type="${empty type ? 'text' : type}"
                       ${readonly_attribute}
                       class="form-control ${not empty edit_group ? 'edit-group': ''}"
                       value="${property[path]}"
                        tabindex="${attributes.tabindex}"/>
            </c:when>
            <c:otherwise>
                <f:input path="${path}" cssClass="form-control ${not empty edit_group ? 'edit-group' : '' }"
                         type="${empty type ? 'text' : type}"
                         readonly="${readonly}"
                         data-form-focus="${form_focus}"
                         onblur="${dynamicAttributes.onblur}"
                        tabindex="${attributes.tabindex}"/>

            </c:otherwise>
        </c:choose>
        <!-- TODO automatically focus input when first showing it -->
        <c:if test="${not empty show_buttons and show_buttons}">
            <a onclick="sur.form.submitEditGroup('${edit_group}')">
                <i class="fa fa-check fa-lg" style="color:green;"></i>
            </a>
            <a onclick="sur.form.cancelEditGroup('${edit_group}')">
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
        $("#${fn:toLowerCase(property.class.simpleName)}-${path}").parents(".form-group").hide();
    </script>
</c:if>