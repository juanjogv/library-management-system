package co.com.juanjogv.lms.domain;

import jakarta.persistence.EntityManager;

public interface CustomRepository<T, ID> {

    /**
     * Retrieves the {@link EntityManager} used to interact with the persistence context.
     * This method provides access to JPA operations for managing entities.
     *
     * @return The {@link EntityManager} instance.
     */
    EntityManager getEntityManager();

    /**
     * Retrieves the {@link Class} type of the entity managed by the repository.
     * This method is useful for operations that require the entity type, such as
     * creating queries or handling type-specific logic.
     *
     * @return The {@link Class} instance representing the entity type.
     */
    Class<T> getEntityClass();
}
