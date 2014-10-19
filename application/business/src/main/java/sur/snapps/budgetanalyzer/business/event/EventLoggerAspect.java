package sur.snapps.budgetanalyzer.business.event;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Pointcut("execution(* sur.snapps.budgetanalyzer.business..*.*(..)) && args(user,..) && @annotation(logEvent)")
    private void logEventForUserUpdates(User user, LogEvent logEvent) {}

    @Pointcut("execution(* sur.snapps.budgetanalyzer.business..*.*(..)) && args(..) && @annotation(logEvent)")
    private void logEventForUserReturned(LogEvent logEvent) {}

    @AfterReturning(pointcut = "logEventForUserUpdates(user, logEvent)", returning = "subject", argNames = "user,logEvent,subject")
    public void logEvent(User user, LogEvent logEvent, BaseEntity subject) {
        eventManager.create(user, subject, logEvent);
    }

    @AfterReturning(pointcut = "logEventForUserReturned(logEvent) && !execution(* sur.snapps.budgetanalyzer.business..*.*(sur.snapps.budgetanalyzer.domain.user.User,..))", returning = "user", argNames = "user,logEvent" )
    public void logEventWhenReturningUser(User user, LogEvent logEvent) {
        eventManager.create(user, null, logEvent);
    }

}
