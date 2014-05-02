package sur.snapps.budgetanalyzer.web.navigation;

/**
 * User: SUR
 * Date: 2/05/14
 * Time: 6:49
 */
public enum PageLinks {

    DASHBOARD("user/dashboard"),

    INVITE_USER("user/user_invitation");

    private String page;

    private PageLinks(String page) {
        this.page = page;
    }

    public String page() {
        return page;
    }

}
