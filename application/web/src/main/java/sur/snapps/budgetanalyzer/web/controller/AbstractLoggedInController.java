package sur.snapps.budgetanalyzer.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.web.controller.user.UserContext;

/**
 * @author sur
 * @since 19/03/2015
 */
public abstract class AbstractLoggedInController extends AbstractController {

    @Autowired
    protected UserContext userContext;

    @ModelAttribute("currentUser")
    public User currentUser() {
        return userContext.getCurrentUser();
    }


    @ModelAttribute("active_page")
    public abstract String activePage();
}
