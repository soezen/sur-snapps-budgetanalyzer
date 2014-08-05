<%@ taglib prefix="tags-form" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@tag description="Form edit group: read only field with hidden edit field" pageEncoding="UTF-8"%>
<%@attribute name="path" fragment="false" required="true" %>
<%@attribute name="property" fragment="false" type="java.lang.Object" %>
<%@attribute name="edit_property" fragment="false" type="java.lang.Object" %>
<%@attribute name="type" fragment="false" %>
<%@attribute name="input_fields" fragment="false" %>


<c:if test="${empty property}">
    <c:set var="property" value="${sur_readonly_property}" />
</c:if>
<c:if test="${empty edit_property}">
    <c:set var="edit_property" value="${sur_edit_property}" />
</c:if>

<div>
    <!-- output label + field -->
    <tags-form:form-property-output path="${path}" property="${property}" editable="true" edit_group="${sur_property_name}-${path}" type="${type}" />

    <!-- input label + field -->
    <c:choose>
        <c:when test="${not empty input_fields}">
            <c:set var="input_fields_split" value="${fn:split(input_fields, ',')}" />
            <c:forEach var="sub_path" items="${input_fields_split}" varStatus="status">
                <tags-form:form-property-input path="${sub_path}" property="${edit_property}" visible="false" show_buttons="${status.last}" edit_group="${sur_property_name}-${path}" spring_input="false" type="${type}" />
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tags-form:form-property-input path="${path}" property="${edit_property}" visible="false" show_buttons="true" edit_group="${sur_property_name}-${path}" spring_input="false" type="${type}" />
        </c:otherwise>
    </c:choose>
</div>