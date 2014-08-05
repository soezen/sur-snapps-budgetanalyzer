package sur.snapps.budgetanalyzer.web.exception;

import com.google.common.base.Predicates;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import sur.snapps.budgetanalyzer.util.Logger;
import sur.snapps.budgetanalyzer.util.exception.BusinessException;
import sur.snapps.budgetanalyzer.web.navigation.NavigateTo;
import sur.snapps.budgetanalyzer.web.navigation.PageLinks;
import sur.snapps.budgetanalyzer.web.response.ErrorResponse;
import sur.snapps.budgetanalyzer.web.response.ResponseHolder;

import java.util.List;

/**
 * User: SUR
 * Date: 1/05/14
 * Time: 16:30
 */
@Aspect
@Component
public class ExceptionHandlerAspect {


    @Around("execution(java.lang.String sur.snapps.budgetanalyzer.web.controller..*.*(..)) && @annotation(navigateTo)")
    public String handleException(ProceedingJoinPoint joinPoint, NavigateTo navigateTo) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        try {
            String result = (String) joinPoint.proceed();
            requestAttributes.setAttribute("shown_success", 0, RequestAttributes.SCOPE_SESSION);
            requestAttributes.setAttribute("success", true, RequestAttributes.SCOPE_SESSION);
            requestAttributes.setAttribute("success_message", navigateTo.successMessage(), RequestAttributes.SCOPE_SESSION);
            return result;
        } catch (Throwable throwable) {
            requestAttributes.setAttribute("shown_error", 0, RequestAttributes.SCOPE_SESSION);
            requestAttributes.setAttribute("error", true, RequestAttributes.SCOPE_SESSION);
            requestAttributes.setAttribute("error_message", navigateTo.errorMessage(), RequestAttributes.SCOPE_SESSION);

            List<Throwable> causalChain = Throwables.getCausalChain(throwable);
            Iterable<Throwable> throwables = Iterables.filter(causalChain, Predicates.instanceOf(BusinessException.class));

            if (throwables.iterator().hasNext()) {
                BusinessException businessException = (BusinessException) throwables.iterator().next();
                Logger.error(businessException.getErrorCode() + " : " + businessException.getMessage());
                requestAttributes.setAttribute("error_items", Lists.newArrayList(businessException.getErrorCode()), RequestAttributes.SCOPE_SESSION);
            } else {
                throwable.printStackTrace();
                return PageLinks.UNEXPECTED_ERROR.page();
            }
        }
        return navigateTo.value().page();
    }

    @Around("execution(sur.snapps.budgetanalyzer.web.response.ResponseHolder sur.snapps.budgetanalyzer.web.controller..*.*(..))")
    public ResponseHolder handleAjaxException(ProceedingJoinPoint joinPoint) {
        try {
            return (ResponseHolder) joinPoint.proceed();
        } catch (Throwable throwable) {
            List<Throwable> causalChain = Throwables.getCausalChain(throwable);
            Iterable<Throwable> throwables = Iterables.filter(causalChain, Predicates.instanceOf(BusinessException.class));

            if (throwables.iterator().hasNext()) {
                BusinessException businessException = (BusinessException) throwables.iterator().next();
                Logger.error(businessException.getErrorCode() + " : " + businessException.getMessage());
                return new ErrorResponse(businessException.getErrorMessage());
            }
        }
        return new ErrorResponse("unexpected error");
    }

    @Before("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))")
    public void clearMessages() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Object shownSuccess = requestAttributes.getAttribute("shown_success", RequestAttributes.SCOPE_SESSION);

        if (shownSuccess != null && (int) shownSuccess == 2) {
            requestAttributes.removeAttribute("shown_success", RequestAttributes.SCOPE_SESSION);
            requestAttributes.removeAttribute("success", RequestAttributes.SCOPE_SESSION);
            requestAttributes.removeAttribute("success_message", RequestAttributes.SCOPE_SESSION);
        } else if (shownSuccess != null) {
            requestAttributes.setAttribute("shown_success", (int) shownSuccess + 1, RequestAttributes.SCOPE_SESSION);
        }

        Object shownError = requestAttributes.getAttribute("shown_error", RequestAttributes.SCOPE_SESSION);

        if (shownError != null && (int) shownError == 2) {
            requestAttributes.removeAttribute("error", RequestAttributes.SCOPE_SESSION);
            requestAttributes.removeAttribute("error_message", RequestAttributes.SCOPE_SESSION);
            requestAttributes.removeAttribute("error_items", RequestAttributes.SCOPE_SESSION);
        } else if (shownError != null) {
            requestAttributes.setAttribute("shown_error", (int) shownError + 1, RequestAttributes.SCOPE_SESSION);
        }
    }

    @Around("execution(java.lang.String sur.snapps.budgetanalyzer.web.controller..*.*(.., org.springframework.validation.BindingResult, ..)) && args(.., bindingResult) && @annotation(navigateTo)")
    public String handleException(ProceedingJoinPoint joinPoint, BindingResult bindingResult, NavigateTo navigateTo) throws Throwable {
        try {
            return (String) joinPoint.proceed();
        } catch (Throwable exception) {
            List<Throwable> causalChain = Throwables.getCausalChain(exception);
            Iterable<Throwable> throwables = Iterables.filter(causalChain, Predicates.instanceOf(BusinessException.class));
            if (throwables.iterator().hasNext()) {
                BusinessException businessException = (BusinessException) throwables.iterator().next();
                Logger.error(businessException.getErrorCode() + " : " + businessException.getMessage());
                bindingResult.reject(businessException.getErrorCode());
            } else {
                exception.printStackTrace();
                Logger.error(exception.getMessage());
            }
        }
        return navigateTo.value().page();
    }

}
