package sur.snapps.budgetanalyzer.persistence;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by sur on 13/02/2015.
 */
public class UUIDGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        return uuid;
    }
}
