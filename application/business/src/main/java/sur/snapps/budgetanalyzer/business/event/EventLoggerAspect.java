package sur.snapps.budgetanalyzer.business.event;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
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

    @AfterReturning("execution(* sur.snapps.budgetanalyzer.business..*.*(..)) && args(user,..) && @annotation(logEvent)")
    public void wrapException(JoinPoint joinPoint, User user, LogEvent logEvent) {
        eventManager.create(user, logEvent);
    }
}
