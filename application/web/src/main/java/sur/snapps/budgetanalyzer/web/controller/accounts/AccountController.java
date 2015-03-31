package sur.snapps.budgetanalyzer.web.controller.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sur.snapps.budgetanalyzer.business.account.AccountManager;
import sur.snapps.budgetanalyzer.business.account.EditAccountView;
import sur.snapps.budgetanalyzer.business.exception.BusinessException;
import sur.snapps.budgetanalyzer.web.controller.AbstractLoggedInController;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;

import javax.validation.Valid;

/**
 * @author sur
 * @since 16/03/2015
 */
@Controller
@RequestMapping("/accounts")
public class AccountController extends AbstractLoggedInController {

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private AccountValidator accountValidator;

    @Override
    public String activePage() {
        return "accounts";
    }

    // TODO link account to user instead of entity
    // owner and admin can modify accounts
    // perhaps later also other account settings (what is visible to others, can other users use this account, ...)

    // TODO set user id not on page but in post method

    @RequestMapping("/overview")
    public String openAccountsOverview(Model model) {
        model.addAttribute("accounts", accountManager.findFor(userContext.getCurrentEntity()));
        return PageLinks.ACCOUNTS_OVERVIEW.page();
    }

    // TODO make response so it can be easily used in smartphone
    // TODO look at loading times of pages

    @RequestMapping("/create")
    public String openCreateAccountPage(Model model) {
        model.addAttribute("account", new EditAccountView());
        return PageLinks.ACCOUNTS_CREATE.page();
    }

    @RequestMapping(value = "/postCreateAccount", method = RequestMethod.POST)
    @NavigateTo(PageLinks.ACCOUNTS_CREATE)
    public String createAccount(@ModelAttribute("account") @Valid EditAccountView account, BindingResult bindingResult) {
        validateCreateAccountInput(account, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BusinessException("form.errors.validation");
        }

        account.setOwnerId(currentUser().getId());
        accountManager.create(account);
        return PageLinks.ACCOUNTS_OVERVIEW.redirect();
    }

    private void validateCreateAccountInput(EditAccountView account, Errors errors) {
        accountValidator.validate(account, errors);
    }
}
