package sur.snapps.budgetanalyzer.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.web.controller.user.UserContext;

/**
 * User: SUR
 * Date: 25/04/14
 * Time: 21:04
 */
public abstract class AbstractController {

    @Autowired
    protected UserContext userContext;

    @ModelAttribute("user")
    public User currentUser() {
        return userContext.getCurrentUser();
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
