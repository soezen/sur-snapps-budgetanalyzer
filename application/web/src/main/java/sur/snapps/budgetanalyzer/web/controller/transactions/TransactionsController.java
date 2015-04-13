package sur.snapps.budgetanalyzer.web.controller.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sur.snapps.budgetanalyzer.business.account.AccountManager;
import sur.snapps.budgetanalyzer.business.exception.BusinessException;
import sur.snapps.budgetanalyzer.business.transaction.EditPurchaseView;
import sur.snapps.budgetanalyzer.business.transaction.TransactionManager;
import sur.snapps.budgetanalyzer.web.controller.AbstractLoggedInController;
import sur.snapps.budgetanalyzer.web.controller.store.StoreController;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author sur
 * @since 17/03/2015
 */
@Controller
@RequestMapping("/transactions")
public class TransactionsController extends AbstractLoggedInController {

    @Autowired
    private StoreController storeController;

    @Autowired
    private TransactionManager transactionManager;
    @Autowired
    private AccountManager accountManager;

    @Autowired
    private PurchaseValidator purchaseValidator;

    @Override
    public String activePage() {
        return "transactions";
    }

    @RequestMapping("/overview")
    public String openAccountsOverview() {
        return PageLinks.TRANSACTIONS_OVERVIEW.page();
    }

    @RequestMapping("/purchase")
    public String openPurchasePage(Model model) {
        model.addAttribute("purchase", new EditPurchaseView());
        model.addAttribute("stores", storeController.findStores());
        model.addAttribute("accounts", accountManager.findFor(userContext.getCurrentEntity()));
        return PageLinks.TRANSACTIONS_PURCHASE.page();
    }

    @RequestMapping(value = "/postPurchase", method = RequestMethod.POST)
    @NavigateTo(PageLinks.TRANSACTIONS_PURCHASE)
    public String purchase(@ModelAttribute("purchase") EditPurchaseView purchase, BindingResult bindingResult) {
        checkNotNull(purchase, "Purchase cannot be null");
        purchaseValidator.validate(purchase, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new BusinessException("form.errors.validation");
        }

        transactionManager.create(purchase);
        return PageLinks.TRANSACTIONS_OVERVIEW.redirect();
    }

}