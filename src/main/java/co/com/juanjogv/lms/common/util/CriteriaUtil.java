package co.com.juanjogv.lms.common.util;

import jakarta.validation.constraints.NotNull;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import jakarta.persistence.criteria.Path;

import java.lang.reflect.Field;
import java.util.Objects;

public final class CriteriaUtil {

    private CriteriaUtil() {}

    public static <T> void buildCriteriaQuery(@NotNull HibernateCriteriaBuilder cb, @NotNull JpaCriteriaQuery<T> cq, @NotNull JpaRoot<?> root) {
        Objects.requireNonNull(cb);
        Objects.requireNonNull(cq);
        Objects.requireNonNull(root);

        Class<T> projectionClass = cq.getResultType();
        Field[] fields = projectionClass.getDeclaredFields();

        Path<?>[] selections = new Path<?>[fields.length];
        for (int i = 0; i < fields.length; i++) {
            selections[i] = root.get(fields[i].getName());
        }

        cq.select(cb.construct(projectionClass, selections));
    }
}
