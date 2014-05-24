package sur.snapps.budgetanalyzer.persistence.user;

import sur.snapps.budgetanalyzer.domain.user.Entity;
import sur.snapps.budgetanalyzer.domain.user.Token;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 15:53
 */
public class TokenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Token save(Token token) {
        token = entityManager.merge(token);
        entityManager.persist(token);
        return token;
    }

    public void delete(Token token) {
        entityManager.remove(token);
    }

    public Token findTokenById(int tokenId) {
        return entityManager.find(Token.class, tokenId);
    }

    public Token findTokenByValue(String tokenValue) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Token> query = builder.createQuery(Token.class);
        Predicate condition = builder.equal(
                query.from(Token.class).get("value"),
                tokenValue);
        query.where(condition);
        return entityManager.createQuery(query).getSingleResult();
    }

    public List<Token> findTokensForEntity(Entity entity) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Token> query = builder.createQuery(Token.class);
        Predicate condition = builder.equal(
                query.from(Token.class).get("entity"),
                entity);
        query.where(condition);

        return entityManager.createQuery(query).getResultList();
    }
}
