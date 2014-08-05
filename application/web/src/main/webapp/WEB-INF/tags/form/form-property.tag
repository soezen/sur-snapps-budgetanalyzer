<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Form property" pageEncoding="UTF-8"%>

<%@attribute name="property" fragment="false" required="false" type="java.lang.Object" %>
<%@attribute name="edit_property" fragment="false" required="false" type="java.lang.Object" %>
<%@attribute name="name" fragment="false" required="false" %>
<%@variable name-given="sur_readonly_property" variable-class="java.lang.Object" scope="AT_END" %>
<%@variable name-given="sur_edit_property" variable-class="java.lang.Object" scope="AT_END" %>
<%@variable name-given="sur_property_name" variable-class="java.lang.String" %>

<c:set var="sur_readonly_property" value="${property}" scope="request" />
<c:set var="sur_edit_property" value="${edit_property}" scope="request" />
<c:set var="sur_property_name" value="${name}" scope="request" />
