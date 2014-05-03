package sur.snapps.budgetanalyzer.web.navigation;

/**
 * User: SUR
 * Date: 2/05/14
 * Time: 6:49
 */
public enum PageLinks {

    USER_REGISTRATION("user_registration", "user/dashboard", "user_registration"),

    DASHBOARD("user/dashboard", null, null),

    MANAGE_USERS("user/manage_users", "user/manageUsers", "user/manage_users"),
    INVITE_USER("user/user_invitation", "user/manageUsers", "user/user_invitation");

    private static final String REDIRECT = "redirect:";
    private static final String CONTEXT = "/budgetanalyzer/";

    private String page;
    private String confirmation;
    private String error;

    private PageLinks(String page, String confirmation, String error) {
        this.page = page;
        this.confirmation = confirmation;
        this.error = error;
    }

    public String error() {
        return error;
    }

    // TODO use mail mock which display a page with the email content (so you can click on the link) --> test content of mail

    public String confirmation() {
        return REDIRECT + CONTEXT + confirmation;
    }

    public String page() {
        return page;
    }

}
