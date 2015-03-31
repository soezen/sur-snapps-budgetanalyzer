var sur = sur || {};

sur.menu = (function() {

    function selectActiveItem(activePage) {
        var menu = $('#menu').find('ul.nav');
        var activeItem = menu.find('li[data-page=\'' + activePage + '\']');
        activeItem.addClass('active');
    }

    return {
        selectActiveItem: selectActiveItem
    }
})();