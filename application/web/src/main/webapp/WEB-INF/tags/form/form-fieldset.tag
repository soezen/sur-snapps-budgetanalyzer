<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@tag description="Form fieldset" pageEncoding="UTF-8"%>
<%@attribute name="legendKey" fragment="false" required="false" %>
<%@attribute name="formActions" fragment="true" required="false" %>
<%@attribute name="extension" fragment="true" required="false" %>

<c:set var="body_width" value="7" />
<c:if test="${empty formActions}">
    <c:set var="body_width" value="10" />
</c:if>
<fieldset>
    <c:if test="${not empty legendKey}">
        <legend><fmt:message key="${legendKey}" /></legend>
    </c:if>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-${body_width}">
                <jsp:doBody />
            </div>
            <div class="col-sm-5">
                <div class="form-group pull-right">
                    <jsp:invoke fragment="formActions" />
                </div>
            </div>
        </div>
        <jsp:invoke fragment="extension" />
    </div>
</fieldset>