package co.com.juanjogv.lms.domain.repository;

import co.com.juanjogv.lms.application.dto.Page;
import co.com.juanjogv.lms.application.dto.Pageable;
import co.com.juanjogv.lms.application.dto.QueryExpression;
import co.com.juanjogv.lms.common.util.CriteriaUtil;
import co.com.juanjogv.lms.common.util.EntityUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.StatelessSession;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

/**
 * A generic interface for paginated and sorted queries on entities of type {@code T} with an identifier of type {@code ID}.
 * This interface extends {@link HibernateCustomRepository} and provides methods to retrieve entities with pagination,
 * sorting, and projection capabilities.
 *
 * <p>The {@code PagingAndSortingRepository} offers advanced query building features by combining Hibernate's
 * StatelessSession with JPA Criteria API to handle paging and sorting, including the ability to project entities
 * into DTOs or other result types. It includes support for pagination, filtering, and counting total rows for
 * proper pagination metadata.</p>
 *
 * @param <T>  The type of the entity.
 * @param <ID> The type of the entity's identifier (primary key).
 * @author juanjogv
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("java:S119")
public interface HibernateCustomPagingAndSortingRepository<T, ID> extends HibernateCustomRepository<T, ID> {

    /**
     * Retrieves the {@link StatelessSession} used to interact with Hibernate without session state.
     *
     * @return The {@link StatelessSession} instance.
     */
    default StatelessSession getStatelessSession() {
        return getSession().getSessionFactory().withStatelessOptions().openStatelessSession();
    }

    /**
     * Retrieves a paginated list of entities from the database, applying the given pagination request.
     * The result is a {@link Page} of entities, with metadata about total elements, pages, and navigation flags.
     *
     * @param pageable The pagination and sorting request, including page number, page size, and sorting options.
     * @return A {@link Page} containing the entities and pagination metadata.
     * @throws NullPointerException if the page request is {@code null}.
     */
    default Page<T> findAll(Pageable pageable) {
        HibernateCriteriaBuilder cb = getStatelessSession().getCriteriaBuilder();
        JpaCriteriaQuery<T> cq = cb.createQuery(getEntityClass());
        JpaRoot<T> root = cq.from(getEntityClass());

        cq.select(root);

        Predicate[] predicates = createCriteriaPredicates(cb, root, pageable);
        cq.where(predicates);

        cq.orderBy(cb.asc(root.get("id")));

        Query<T> query = getStatelessSession().createQuery(cq)
                .setFirstResult(pageable.size() * pageable.page())
                .setMaxResults(pageable.size());

        Query<Long> countQuery = createCountQuery(pageable);
        long totalRows = countQuery.getSingleResult();
        long totalPages = totalRows / pageable.size();
        boolean hasPrevious = pageable.page() > 0;
        boolean hasNext = pageable.page() < totalPages;

        return Page.<T>builder()
                .content(query.getResultList())
                .totalElements(totalRows)
                .totalPages(totalPages)
                .hasPrevious(hasPrevious)
                .hasNext(hasNext)
                .build();
    }

    /**
     * Retrieves a paginated list of projected entities (DTOs or other result types) from the database,
     * applying the given pagination request. The result is a {@link Page} of projected entities with metadata.
     *
     * @param pageable        The pagination and sorting request, including page number, page size, and sorting options.
     * @param projectionClass The class of the projection type (DTO or other result type).
     * @param <P>             The type of the projected entity.
     * @return A {@link Page} containing the projected entities and pagination metadata.
     * @throws NullPointerException if the page request or projection class is {@code null}.
     */
    default <P> Page<P> findAll(Pageable pageable, Class<P> projectionClass) {

        HibernateCriteriaBuilder cb = getStatelessSession().getCriteriaBuilder();
        JpaCriteriaQuery<P> cq = cb.createQuery(projectionClass);
        JpaRoot<T> root = cq.from(getEntityClass());
        CriteriaUtil.buildCriteriaQuery(cb, cq, root);

        Predicate[] predicates = createCriteriaPredicates(cb, root, pageable);
        cq.where(predicates);

        cq.orderBy(cb.asc(root.get("id")));

        Query<P> query = getStatelessSession().createQuery(cq)
                .setFirstResult(pageable.size() * pageable.page())
                .setMaxResults(pageable.size());

        Query<Long> countQuery = createCountQuery(pageable);
        long totalRows = countQuery.getSingleResult();
        long totalPages = totalRows / pageable.size();
        boolean hasPrevious = pageable.page() > 0;
        boolean hasNext = pageable.page() < totalPages;

        return Page.<P>builder()
                .content(query.getResultList())
                .totalElements(totalRows)
                .totalPages(totalPages)
                .hasPrevious(hasPrevious)
                .hasNext(hasNext)
                .build();
    }

    /**
     * Creates a list of {@link Predicate} objects based on the query expressions in the given {@link Pageable}.
     * These predicates are used to filter the results based on conditions like equality, inequality, range checks, etc.
     *
     * @param cb       The {@link CriteriaBuilder} instance for building predicates.
     * @param root     The root of the query, representing the entity.
     * @param pageable The page request containing the query expressions.
     * @return An array of {@link Predicate} objects representing the query conditions.
     */
    private Predicate[] createCriteriaPredicates(HibernateCriteriaBuilder cb, JpaRoot<T> root, Pageable pageable) {
        Predicate[] predicates = new Predicate[pageable.queryExpressions().size()];

        for (int i = 0; i < pageable.queryExpressions().size(); i++) {

            final QueryExpression queryExpression = pageable.queryExpressions().get(i);

            predicates[i] = switch (queryExpression.getCondition()) {
                case EQUALS -> cb.equal(root.get(queryExpression.getParameter()), queryExpression.getValue());
                case NOT_EQUALS -> cb.notEqual(root.get(queryExpression.getParameter()), queryExpression.getValue());
                case GREATER_THAN -> cb.greaterThan(root.get(queryExpression.getParameter()), queryExpression.getValue());
                case GREATER_THAN_OR_EQUALS -> cb.greaterThanOrEqualTo(root.get(queryExpression.getParameter()), queryExpression.getValue());
                case LESS_THAN -> cb.lessThan(root.get(queryExpression.getParameter()), queryExpression.getValue());
                case LESS_THAN_OR_EQUALS -> cb.lessThanOrEqualTo(root.get(queryExpression.getParameter()), queryExpression.getValue());
                case IS_NULL -> cb.isNull(root.get(queryExpression.getParameter()));
                case IS_NOT_NULL -> cb.isNotNull(root.get(queryExpression.getParameter()));
                case LIKE -> cb.like(root.get(queryExpression.getParameter()), queryExpression.getValue());
                case NOT_LIKE -> cb.notLike(root.get(queryExpression.getParameter()), queryExpression.getValue());
                case IN, NOT_IN, BETWEEN, NOT_BETWEEN -> handleMultiParameterValue(cb, root, queryExpression);
            };
        }

        return predicates;
    }

    /**
     * Handles query expressions that involve multiple parameter values, such as "IN", "NOT IN", "BETWEEN", and "NOT BETWEEN".
     *
     * @param cb              The {@link HibernateCriteriaBuilder} instance for building the predicate.
     * @param root            The root of the query, representing the entity.
     * @param queryExpression The query expression containing the condition and its values.
     * @return A {@link Predicate} that applies the condition with the multiple values.
     */
    private Predicate handleMultiParameterValue(HibernateCriteriaBuilder cb, JpaRoot<T> root, QueryExpression queryExpression) {
        String[] values = queryExpression.getValue().split(",");

        return switch (queryExpression.getCondition()) {
            case IN -> root.get(queryExpression.getParameter()).in((Object) values);
            case NOT_IN -> cb.not(root.get(queryExpression.getParameter()).in((Object) values));
            case BETWEEN -> cb.between(root.get(queryExpression.getParameter()), values[0], values[1]);
            case NOT_BETWEEN -> cb.not(cb.between(root.get(queryExpression.getParameter()), values[0], values[1]));
            default -> throw new IllegalArgumentException("Condition \"%s\" does not exist".formatted(queryExpression.getCondition()));
        };
    }

    /**
     * Creates a count query to calculate the total number of entities matching the criteria specified in the {@link Pageable}.
     * This count query is used to determine the total number of rows for pagination.
     *
     * @param pageable The page request containing the query expressions.
     * @return A {@link Query} that returns the total count of entities.
     */
    private Query<Long> createCountQuery(Pageable pageable) {
        HibernateCriteriaBuilder cb = getStatelessSession().getCriteriaBuilder();
        JpaCriteriaQuery<Long> cq = cb.createQuery(Long.class);
        JpaRoot<T> root = cq.from(getEntityClass());

        String idColumnName = EntityUtil.getIdColumnName(getEntityClass());
        cq.select(cb.count(root.get(idColumnName)));

        Predicate[] predicates = createCriteriaPredicates(cb, root, pageable);
        cq.where(predicates);

        return getStatelessSession().createQuery(cq);
    }
}
