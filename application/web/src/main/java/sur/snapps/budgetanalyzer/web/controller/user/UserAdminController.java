package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;

/**
 * User: SUR
 * Date: 4/05/14
 * Time: 10:57
 */
@Controller
@RequestMapping("/user/admin")
public class UserAdminController {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserContext userContext;

    @RequestMapping("/removeUser/{userId}")
    public String removeUser(@PathVariable int userId) {
        // TODO do not remove admin user
        // TODO validate that logged in user has access to that user
        // TODO implement removeUser
        System.out.println("Removing user: " + userId);
        return PageLinks.MANAGE_USERS.page();
    }

    @RequestMapping("/restoreInvitation/${tokenId}")
    public String restoreInvitation(@PathVariable int tokenId) {
        // TODO implement restoreInvitation (only when status revoked or expired)
        System.out.println("Restore invitation: " + tokenId);
        return PageLinks.MANAGE_USERS.page();
    }

    @RequestMapping("/extendInvitation/{tokenId}")
    @NavigateTo(PageLinks.MANAGE_USERS)
    public String extendInvitation(@PathVariable int tokenId) {
        PageLinks pageLink = PageLinks.MANAGE_USERS;
        User user = userContext.getCurrentUser();

        tokenManager.extend(user.getId(), tokenId);
        return pageLink.page();
    }

    @RequestMapping("/resendInvitation/{tokenId}")
    public String resendInvitation(@PathVariable int tokenId) {
        // TODO implement resendInvitation
        System.out.println("Resending invitation: " + tokenId);
        return PageLinks.MANAGE_USERS.page();
    }

    @RequestMapping("/revokeInvitation/{tokenId}")
    public String revokeInvitation(@PathVariable int tokenId) {
        // TODO implement revokeInvitation
        System.out.println("Revoke invitation: " + tokenId);
        return PageLinks.MANAGE_USERS.page();
    }
}
