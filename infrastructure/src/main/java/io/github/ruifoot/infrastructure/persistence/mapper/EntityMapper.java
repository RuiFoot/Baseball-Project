package io.github.ruifoot.infrastructure.persistence.mapper;

/**
 * Generic mapper interface for converting between JPA entities and domain models.
 *
 * @param <E> the entity type
 * @param <D> the domain model type
 */
public interface EntityMapper<E, D> {

    /**
     * Converts a JPA entity to a domain model.
     *
     * @param entity the entity to convert
     * @return the corresponding domain model
     */
    D toDomain(E entity);

    /**
     * Converts a domain model to a JPA entity.
     *
     * @param domain the domain model to convert
     * @return the corresponding entity
     */
    E toEntity(D domain);

    /**
     * Updates an existing entity with values from a domain model.
     *
     * @param entity the entity to update
     * @param domain the domain model containing the new values
     * @return the updated entity
     */
    E updateEntityFromDomain(E entity, D domain);
}