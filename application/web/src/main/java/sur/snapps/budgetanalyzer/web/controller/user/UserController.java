package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sur.snapps.budgetanalyzer.business.user.TokenManager;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;

/**
 * UserDashboardController
 *
 * User: SUR
 * Date: 22/04/14
 * Time: 19:43
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserContext userContext;

    @RequestMapping("/dashboard")
    public String openUserDashboard() {
        return PageLinks.DASHBOARD.page();
    }

    @RequestMapping("/profile")
    public String openProfilePage(Model model) {
        Entity entity = userContext.getCurrentUser().getEntity();
        model.addAttribute("user", userContext.getCurrentUser());
        model.addAttribute("users", userManager.findUsersOfEntity(entity));
        // TODO only load these if admin user is logged in
        model.addAttribute("tokens", tokenManager.findTokensForEntity(entity));
        return PageLinks.PROFILE.page();
    }

    @RequestMapping("/disableCurrentUser")
    public String disableCurrentUser() {
        return null;
    }

}
