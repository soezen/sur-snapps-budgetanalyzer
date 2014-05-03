package sur.snapps.budgetanalyzer.web.exception;

import com.google.common.base.Predicates;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import sur.snapps.budgetanalyzer.util.Logger;
import sur.snapps.budgetanalyzer.util.exception.BusinessException;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;

import java.lang.reflect.Method;
import java.util.List;

/**
 * User: SUR
 * Date: 1/05/14
 * Time: 16:30
 */
@Aspect
@Component
public class ExceptionHandlerAspect {

    @Around("execution(java.lang.String sur.snapps.budgetanalyzer.web.controller..*.*(.., org.springframework.validation.BindingResult, ..)) && args(.., bindingResult)")
    public String copyExceptionMessage(ProceedingJoinPoint joinPoint, BindingResult bindingResult) throws Throwable {
        try {
            return (String) joinPoint.proceed();
        } catch (Throwable exception) {
            List<Throwable> causalChain = Throwables.getCausalChain(exception);
            Throwable throwable = Iterables.find(causalChain, Predicates.instanceOf(BusinessException.class));
            if (throwable != null) {
                BusinessException businessException = (BusinessException) throwable;
                Logger.error(businessException.getMessage());
                bindingResult.reject(businessException.getErrorCode());

                MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                Method method = methodSignature.getMethod();

                if (method.isAnnotationPresent(NavigateTo.class)) {
                    NavigateTo annotation = method.getAnnotation(NavigateTo.class);
                    PageLinks link = annotation.value();
                    return link.error();
                }
            }
        }
        return PageLinks.DASHBOARD.page();
    }

}
