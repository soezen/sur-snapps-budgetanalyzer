package sur.snapps.budgetanalyzer.business.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.easymock.annotation.Mock;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.annotation.Dummy;
import sur.snapps.budgetanalyzer.util.exception.BusinessException;

import static org.easymock.EasyMock.expect;
import static org.unitils.easymock.EasyMockUnitils.replay;

/**
 * User: SUR
 * Date: 2/05/14
 * Time: 20:40
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ExceptionWrapperAspectTest {

    @TestedObject
    private ExceptionWrapperAspect aspect;

    @Mock
    private JoinPoint joinPoint;
    @Mock
    private Signature signature;
    @Dummy
    private RuntimeException exception;

    @Test(expected = BusinessException.class)
    public void testWrapException() {
        expect(joinPoint.getSignature()).andReturn(signature);
        expect(signature.getDeclaringTypeName()).andReturn("type");
        expect(signature.getName()).andReturn("name");
        replay();

        aspect.wrapException(joinPoint, exception);
    }
}
