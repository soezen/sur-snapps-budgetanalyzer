<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@tag description="Form input for a property field with label and errors" pageEncoding="UTF-8"%>
<%@attribute name="path" fragment="false" required="true" %>
<%@attribute name="property" fragment="false" required="true" type="java.lang.Object" %>
<%@variable name-given="sur_property" variable-class="java.lang.Object" scope="AT_END" %>


<c:set var="pathProperties" value="${fn:split(path, '.')}" />
<c:set var="sur_property" value="${property}" />
<c:forEach var="pathProperty" items="${pathProperties}">
    <c:set var="sur_property" value="${sur_property[pathProperty]}" />
</c:forEach>