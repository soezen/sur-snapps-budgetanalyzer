package sur.snapps.budgetanalyzer.business.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import sur.snapps.budgetanalyzer.util.exception.BusinessException;

/**
 * User: SUR
 * Date: 1/05/14
 * Time: 15:27
 */
@Aspect
public class ExceptionWrapperAspect {

    @AfterThrowing(value = "execution(* sur.snapps.budgetanalyzer.business..*.*(..))", throwing = "e")
    public void wrapException(JoinPoint joinPoint, RuntimeException e) {
        Signature signature = joinPoint.getSignature();
        if (!(e instanceof BusinessException)) {
            throw new BusinessException(signature.getDeclaringTypeName() + "." + signature.getName() + " : " + e.getMessage(), e);
        }
        throw e;
    }
}
