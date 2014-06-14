package sur.snapps.budgetanalyzer.business.event;

import sur.snapps.budgetanalyzer.domain.event.EventType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: SUR
 * Date: 14/06/14
 * Time: 8:46
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogEvent {

    EventType value();
}
