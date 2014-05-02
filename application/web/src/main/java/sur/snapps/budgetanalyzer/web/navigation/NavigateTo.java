package sur.snapps.budgetanalyzer.web.navigation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: SUR
 * Date: 2/05/14
 * Time: 6:55
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NavigateTo {

    PageLinks value() default PageLinks.DASHBOARD;
}
