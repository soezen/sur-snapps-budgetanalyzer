package sur.snapps.budgetanalyzer.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 20:27
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {

    @Autowired
    private UserManager userManager;

    private User user;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (user != null && username.equalsIgnoreCase(user.username())) {
            return user;
        }
        user = userManager.findByUsername(username);
        return user;
    }

    public Entity getCurrentEntity() {
        if (user == null) {
            getCurrentUser();
        }
        return user == null ? null : user.entity();
    }

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public void reset() {
        user = null;
    }
}
