package sur.snapps.budgetanalyzer.persistence;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.exception.RevisionDoesNotExistException;
import org.hibernate.envers.query.AuditEntity;
import sur.snapps.budgetanalyzer.domain.BaseAuditedEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * @author sur
 * @since 13/03/2015
 */
public class HistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public <T extends BaseAuditedEntity> T find(Class<T> entityClass, String id, Date tms) {
        try {
            AuditReader auditReader = AuditReaderFactory.get(entityManager);
            return (T) auditReader.createQuery()
                .forEntitiesAtRevision(entityClass, auditReader.getRevisionNumberForDate(tms).intValue() + 1)
                .add(AuditEntity.property("id").eq(id))
                .getSingleResult();
        } catch (RevisionDoesNotExistException | NoResultException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
