<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Accordion" pageEncoding="UTF-8"%>
<%@attribute name="parentId" fragment="false" required="true" %>
<%@attribute name="id" fragment="false" required="true" %>
<%@attribute name="title" fragment="true" required="true" %>

<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#${parentId}" href="#${id}">
                <jsp:invoke fragment="title" />
            </a>
        </h4>
    </div>
    <div id="${id}" class="panel-collapse collapse">
        <div class="panel-body">
            <jsp:doBody />
        </div>
    </div>
</div>