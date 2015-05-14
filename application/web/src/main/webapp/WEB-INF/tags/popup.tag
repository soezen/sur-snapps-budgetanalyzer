<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="id" fragment="false" required="true" %>
<%@attribute name="title" fragment="false" required="true" %>
<%@attribute name="footer" fragment="true" %>

<div id="${id}" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><fmt:message key="${title}" /></h4>
            </div>
            <div class="modal-body">
                <jsp:doBody />
            </div>
            <div class="modal-footer">
                <jsp:invoke fragment="footer" />
            </div>
        </div>
    </div>
</div>