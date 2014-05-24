package sur.snapps.budgetanalyzer.web.navigation;

/**
 * User: SUR
 * Date: 2/05/14
 * Time: 6:49
 */
public enum PageLinks {

    USER_REGISTRATION("user_registration", "userRegistration"),
    USER_REGISTRATION_SUCCESS("user_registration_success", "userRegistrationSuccess"),

    DASHBOARD("user/dashboard", "user/dashboard"),

    MANAGE_USERS("user/manage_users", "user/manageUsers"),
    INVITE_USER("user/user_invitation", "user/inviteUser");

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
