package sur.snapps.budgetanalyzer.business.event;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import sur.snapps.budgetanalyzer.business.user.UserManager;
import sur.snapps.budgetanalyzer.domain.BaseEntity;
import sur.snapps.budgetanalyzer.domain.user.User;

/**
 * User: SUR
 * Date: 1/05/14
 * Time: 15:27
 */
@Aspect
public class EventLoggerAspect {

    @Autowired
    private EventManager eventManager;

    @Autowired
    private UserManager userManager;

    @Pointcut("execution(* sur.snapps.budgetanalyzer.business..*.*(..)) && args(..) && @annotation(logEvent)")
    private void logEventForUserUpdates(LogEvent logEvent) {}

    @AfterReturning(pointcut = "logEventForUserUpdates(logEvent)", returning = "subject", argNames = "logEvent,subject")
    public void logEvent(LogEvent logEvent, BaseEntity subject) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userManager.findByUsername(username);
        eventManager.create(user, subject, logEvent);
    }
}
