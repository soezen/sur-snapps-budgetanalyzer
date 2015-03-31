<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Accordion" pageEncoding="UTF-8"%>
<%@attribute name="parentId" fragment="false" required="true" %>
<%@attribute name="id" fragment="false" required="true" %>
<%@attribute name="title" fragment="true" required="true" %>
<%@attribute name="button" fragment="true" %>

<div class="panel panel-default">
    <div class="panel-heading">
        <jsp:invoke fragment="button" />
        <a data-toggle="collapse" data-parent="#${parentId}" href="#${id}">
            <h4 class="panel-title">
                <jsp:invoke fragment="title" />
            </h4>
        </a>
    </div>
    <div id="${id}" class="panel-collapse collapse">
        <div class="panel-body" data-alert="catch">
            <jsp:doBody />
        </div>
    </div>
</div>