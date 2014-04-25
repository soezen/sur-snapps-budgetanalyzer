package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sur.snapps.budgetanalyzer.web.controller.AbstractController;

/**
 * UserDashboardController
 *
 * User: SUR
 * Date: 22/04/14
 * Time: 19:43
 */
@Controller
@RequestMapping("/user")
public class UserDashboardController extends AbstractController {

    @RequestMapping("/dashboard")
    public String openUserDashboard() {
        return "user/dashboard";
    }
}
