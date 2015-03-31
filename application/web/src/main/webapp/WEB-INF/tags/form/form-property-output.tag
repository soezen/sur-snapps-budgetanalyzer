<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@tag description="Form output of a property field with label" pageEncoding="UTF-8"%>
<%@attribute name="path" fragment="false" required="true" %>
<%@attribute name="property" type="java.lang.Object" fragment="false" %>
<%@attribute name="type" fragment="false" %>
<%@attribute name="editable" fragment="false" %>
<%@attribute name="edit_group" fragment="false" %>

<c:if test="${empty property}">
    <c:set var="property" value="${sur_readonly_property}" />
</c:if>
<c:if test="${empty edit_property}">
    <c:set var="edit_property" value="${sur_edit_property}" />
</c:if>

<c:if test="${empty edit_group}">
    <c:set var="edit_group" value="${path}" />
</c:if>
<div class="form-group" data-edit-group-readonly="${edit_group}">
    <label for="${path}" class="col-sm-3 control-label">
        <fmt:message key="${fn:toLowerCase(property.class.simpleName)}.${path}" />
    </label>
    <div class="col-sm-9">
        <p class="form-control-static">
            <span class="edit-group-value">
                <c:choose>
                    <c:when test="${type ne 'password'}">
                        <sur:set-property-value path="${path}" property="${property}" />
                        <c:out value="${sur_property}" />
                    </c:when>
                    <c:otherwise>
                        <c:out value="**********" />
                    </c:otherwise>
                </c:choose>
            </span>
            <c:if test="${not empty editable and editable}">
                <a onclick="sur.form.edit(event, '${edit_group}')">
                    <i class="fa fa-pencil-square-o"></i>
                </a>
            </c:if>
        </p>
    </div>
</div>