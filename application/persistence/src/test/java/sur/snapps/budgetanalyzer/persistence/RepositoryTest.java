package sur.snapps.budgetanalyzer.persistence;

import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import sur.snapps.jetta.data.JettaDataRule;
import sur.snapps.jetta.data.spring.JettaDataLoaderTestExecutionListener;
import sur.snapps.jetta.database.JettaDatabaseRule;
import sur.snapps.jetta.database.counter.RecordCounter;
import sur.snapps.jetta.database.script.Script;

/**
 * Abstract repository test.
 * - default spring context
 *
 * Created by sur on 18/02/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    JettaDataLoaderTestExecutionListener.class
})
@Transactional
@ContextConfiguration({
    "classpath*:db-config-test.xml",
    "classpath*:persistence-config.xml"})
@Script("initDB.sql")
public abstract class RepositoryTest {

    @Rule
    public JettaDataRule dataRule = new JettaDataRule(this);

    @Rule
    public JettaDatabaseRule databaseRule = new JettaDatabaseRule(this);

    protected RecordCounter counter;

}
