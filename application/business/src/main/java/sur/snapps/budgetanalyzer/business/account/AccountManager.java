package sur.snapps.budgetanalyzer.business.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.budgetanalyzer.business.event.LogEvent;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.event.EventType;
import sur.snapps.budgetanalyzer.domain.user.Account;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.account.AccountRepository;

import java.util.List;

/**
 * User: SUR
 * Date: 21/04/14
 * Time: 16:32
 */
public class AccountManager {

    @Autowired
    private UserManager userManager;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @LogEvent(EventType.ACCOUNT_CREATED)
    public Account create(EditAccountView inputAccount) {
        User user = userManager.findById(inputAccount.getOwnerId());

        Account account = Account.newAccount()
            .name(inputAccount.getName())
            .owner(user)
            .build();
        return accountRepository.save(account);
    }

    public List<Account> findFor(Entity entity) {
        return accountRepository.findFor(entity.getId());
    }
}
