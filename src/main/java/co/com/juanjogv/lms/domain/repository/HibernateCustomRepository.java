package co.com.juanjogv.lms.domain.repository;

import co.com.juanjogv.lms.domain.CustomRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface HibernateCustomRepository<T, ID> extends CustomRepository<T, ID> {

    default SessionFactory getSessionFactory(){
        return getSession().getSessionFactory();
    }

    default Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    /**
     * Retrieves the {@link Class} type of the entity managed by the repository.
     * This method is useful for operations that require the entity type, such as
     * creating queries or handling type-specific logic.
     *
     * @return The {@link Class} instance representing the entity type.
     */
    Class<T> getEntityClass();
}
