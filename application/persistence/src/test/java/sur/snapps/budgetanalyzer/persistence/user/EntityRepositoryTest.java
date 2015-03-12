package sur.snapps.budgetanalyzer.persistence.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.persistence.RepositoryTest;
import sur.snapps.jetta.data.DataSet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class EntityRepositoryTest extends RepositoryTest {

    @Autowired
    private EntityRepository repository;

    @Test
    @DataSet(type = Entity.class)
    public void findById() {
        assertNotNull(repository.findById("SIMPSONS"));
    }

    @Test
    public void findByIdNoResult() {
        assertNull(repository.findById("NOT_FOUND"));
    }

}