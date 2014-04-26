package sur.snapps.budgetanalyzer.persistence.user;

import sur.snapps.budgetanalyzer.domain.user.Token;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 15:53
 */
public class TokenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Token save(Token token) {
        entityManager.persist(token);
        return token;
    }
}
