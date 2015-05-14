<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sur" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="css" fragment="true" %>

<sur:html>
    <jsp:attribute name="css">
        <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/dashboard.css" />">
        <jsp:invoke fragment="css" />
    </jsp:attribute>
    <jsp:body>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a id="menu_dashboard" class="navbar-brand" href="<c:url value="/budgetanalyzer/user/dashboard" />">
                        <fmt:message key="application.name" />
                    </a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li class="navbar-text">${currentUser.name} (${currentUser.entity.name})</li>
                        <li><a href="#">Settings</a></li>
                        <li><a href="#">Help</a></li>
                        <li><a id="menu_logout" href="<c:url value="/j_spring_security_logout"/>"><fmt:message key="menu.logout" /></a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row">
                <div id="menu" class="col-sm-3 col-md-2 sidebar">
                    <ul class="nav nav-sidebar">
                        <li data-page="transactions">
                            <a id="menu_transactions" href="<c:url value="/budgetanalyzer/transactions/overview" />">
                                <fmt:message key="menu.transactions" />
                            </a>
                            <ul class="nav nav-sidebar">
                                <li>
                                    <a href="<c:url value="/budgetanalyzer/transactions/purchase" />">
                                        <i class="fa fa-lg fa-plus"></i>
                                        <fmt:message key="menu.purchase" />
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li data-page="accounts">
                            <a id="menu_accounts" href="<c:url value="/budgetanalyzer/accounts/overview" />">
                                <fmt:message key="menu.accounts" />
                            </a>
                            <ul class="nav nav-sidebar">
                                <li><a href="<c:url value="/budgetanalyzer/accounts/overview" />"><i class="fa fa-lg fa-bars"></i> Overview</a></li>
                                <li><a href="<c:url value="/budgetanalyzer/accounts/create" />"><i class="fa fa-lg fa-plus"></i> Create New</a></li>
                            </ul>
                        </li>
                        <li><a href="#">Budgets</a></li>
                        <li data-page="products">
                            <a id="menu_products" href="<c:url value="/budgetanalyzer/products/overview" />">
                                <fmt:message key="menu.products" />
                            </a>
                        </li>
                    </ul>
                    <ul class="nav nav-sidebar">
                        <li><a href="">Reports</a></li>
                        <li><a href="">Item 2</a></li>
                        <li><a href="">Item 3</a></li>
                        <li><a href="">Item 4</a></li>
                        <li><a href="">Item 5</a></li>
                    </ul>
                    <ul class="nav nav-sidebar">
                        <li class="nav-divider"></li>
                        <li data-page="profile">
                            <a id="menu_profile" href="<c:url value="/budgetanalyzer/user/profile" />"><fmt:message key="menu.profile" /></a>
                        </li>
                        <s:authorize ifAllGranted="ROLE_ADMIN">
                            <li data-page="entity_settings">
                                <a href="<c:url value="/budgetanalyzer/user/admin/entitySettings" />"><fmt:message key="menu.entity_settings" /></a>
                                <ul class="nav nav-sidebar">
                                    <li>
                                        <a href="<c:url value="/budgetanalyzer/user/admin/inviteUser" />">
                                            <i class="fa fa-lg fa-plus"></i>
                                            <fmt:message key="menu.user_invitation" />
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </s:authorize>
                    </ul>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" data-alert="catch">
                    <jsp:doBody />
                </div>
            </div>
        </div>

        <script src="<c:url value="/resources/js/sur.menu.js" />"></script>
        <script>
            $(document).ready(function() {
                sur.baseUrl = '<c:url value="/budgetanalyzer/" />';
                sur.menu.selectActiveItem('${active_page}');
            });
        </script>
    </jsp:body>
</sur:html>
