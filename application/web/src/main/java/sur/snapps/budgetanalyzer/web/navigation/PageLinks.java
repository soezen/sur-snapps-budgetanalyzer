package sur.snapps.budgetanalyzer.web.navigation;

/**
 * User: SUR
 * Date: 2/05/14
 * Time: 6:49
 */
public enum PageLinks {

    UNEXPECTED_ERROR("unexpected_error", "unexpectedError"),

    HOMEPAGE("homepage", "homepage"),
    LOGIN("login", "login"),
    DENIED("denied", "denied"),

    USER_REGISTRATION("user_registration", "userRegistration"),
    USER_REGISTRATION_SUCCESS("user_registration_success", "userRegistrationSuccess"),
    INVALID_TOKEN("invalid_token", "invalidToken"),

    DASHBOARD("user/dashboard", "user/dashboard"),

    PROFILE("user/profile", "user/profile"),
    INVITE_USER("user/admin/user_invitation", "user/admin/inviteUser"),
    ENTITY_SETTINGS("user/admin/entity_settings", "user/admin/entitySettings"),

    ACCOUNTS_OVERVIEW("accounts/overview", "accounts/overview"),
    ACCOUNTS_CREATE("accounts/create", "accounts/create"),

    TRANSACTIONS_OVERVIEW("transactions/overview", "transactions/overview"),
    TRANSACTIONS_PURCHASE("transactions/purchase", "transactions/purchase"),

    PRODUCT_SEARCH("transactions/search_product", "products/search"),
    PRODUCTS_OVERVIEW("products/overview", "products/overview"),

    CATEGORY_CREATE("products/new_category", "products/openNewCategoryPopup")
    ;

    private static final String REDIRECT = "redirect:";
    private static final String CONTEXT = "/budgetanalyzer/";

    private String page;
    private String redirect;

    private PageLinks(String page, String redirect) {
        this.page = page;
        this.redirect = redirect;
    }

    // TODO use mail mock which display a page with the email content (so you can click on the link) --> test content of mail

    public String redirect() {
        return REDIRECT + CONTEXT + redirect;
    }

    public String page() {
        return page;
    }

}
