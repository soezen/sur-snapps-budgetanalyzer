package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: SUR
 * Date: 22/04/14
 * Time: 19:43
 */
@Controller
@RequestMapping("/user")
public class UserDashboardController {

    @RequestMapping("/dashboard")
    public String openUserDashboard() {
        return "user/dashboard";
    }
}
