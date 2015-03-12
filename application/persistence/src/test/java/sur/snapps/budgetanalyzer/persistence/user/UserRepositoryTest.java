package sur.snapps.budgetanalyzer.persistence.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.User;
import sur.snapps.budgetanalyzer.persistence.RepositoryTest;
import sur.snapps.jetta.data.DataObject;
import sur.snapps.jetta.data.DataSet;
import sur.snapps.jetta.database.counter.expression.conditional.Conditional;
import sur.snapps.jetta.database.counter.expression.conditional.Conditionals;
import sur.snapps.jetta.database.counter.table.Column;
import sur.snapps.jetta.database.counter.table.Table;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * UserRepository tests.
 *
 * Created by sur on 18/02/2015.
 */
@DataSet(type = User.class, selector = "entity@id", value = {"SIMPSONS", "A_TEAM"})
public class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EntityRepository entityRepository;

    @DataObject("SIMPSONS")
    private Entity simpsons;
    @DataObject(value = "the Flanders", identifier = "name")
    private Entity flanders;
    @DataObject("A_TEAM")
    private Entity aTeam;
    @DataObject("HOMER_SIMPSON")
    private User homer;

    @Test
    public void findUsersOfEntity() {
        assertEquals(1, repository.findUsersOfEntity(simpsons).size());
        assertEquals(3, repository.findUsersOfEntity(aTeam).size());
    }

    @Test
    public void findById() {
        assertEquals(homer, repository.findById("HOMER_SIMPSON"));
    }

    @Test
    public void findByIdNoResult() {
        assertNull(repository.findById("NOT_FOUND"));
    }

    @Test
    public void findByUsername() {
        assertEquals(homer, repository.findByUsername("homer"));
    }

    @Test
    public void findByUsernameNoResult() {
        assertNull(repository.findByUsername("nobody"));
    }

    @Test
    public void isUsernameUsedTrue() {
        assertTrue(repository.isUsernameUsed("homer"));
    }

    @Test
    public void isUsernameUsedFalse() {
        assertFalse(repository.isUsernameUsed("nobody"));
    }

    @Test
    public void saveAdmin() {
        User newUser = User.createAdmin()
            .username("ned.flanders")
            .password("test")
            .name("Ned Flanders")
            .entity(flanders)
            .email("ned.flanders@springfield.com")
            .build();

        repository.save(newUser);
        repository.flush();

        // TODO put these assertions in helper classes
        Table usersTable = new Table("USERS", "u", "id");
        Column usernameColumn = usersTable.column("username");
        Conditional usernameEqualsGrandpa = Conditionals.equal(usernameColumn, "'ned.flanders'");
        assertEquals(1, counter.count().from(usersTable)
            .where(usernameEqualsGrandpa)
            .get());
        usersTable.join(new Table("AUTHORITIES", "a", "USER_ID"), "USER_ID");
        assertEquals(2, counter.count().from(usersTable)
            .where(usernameEqualsGrandpa)
            .get());
        Table entitiesTable = new Table("ENTITIES");
        Column nameColumn = entitiesTable.column("NAME");
        assertEquals(1, counter.count().from(entitiesTable)
            .where(Conditionals.equal(nameColumn, "'the Flanders'"))
            .get());

    }

    @Test
    public void saveUser() {
        Entity managedSimpsons = entityRepository.findById("SIMPSONS");
        User newUser = User.createUser()
            .email("grandpa.simpson@springfield.com")
            .entity(managedSimpsons)
            .name("Grandpa Simpson")
            .password("test")
            .username("grandpa")
            .build();

        repository.save(newUser);
        // TODO remove flush from repository since only to be used in tests
        repository.flush();

        Table usersTable = new Table("USERS", "u", "id");
        Column usernameColumn = usersTable.column("username");
        Conditional usernameEqualsGrandpa = Conditionals.equal(usernameColumn, "'grandpa'");
        assertEquals(1, counter.count().from(usersTable)
            .where(usernameEqualsGrandpa)
            .get());
        usersTable.join(new Table("AUTHORITIES", "a", "USER_ID"), "USER_ID");
        assertEquals(1, counter.count().from(usersTable)
            .where(usernameEqualsGrandpa)
            .get());
    }

}
