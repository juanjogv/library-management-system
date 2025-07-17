package co.com.juanjogv.lms.common.util;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Objects;

public final class EntityUtil {

    private static final String VALID_USER_DEFINED_TYPE_PACKAGE = "co.com.juanjogv";
    private static final MessageFormat NOT_USER_DEFINED_TYPE_MESSAGE_FORMAT = new MessageFormat("Cannot access properties of non-user-defined types, Trying to access properties of {0} via {1} ");

    private EntityUtil() {}

    public static void validateParamAccessor(@NotNull Class<?> clazz, @NotNull String paramAccessor) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(paramAccessor);

        String[] accessDepth = paramAccessor.split("\\.");

        Field field = null;
        String fieldTypeName = null;

        for (int i = 0; i < accessDepth.length; i++) {
            try {
                String accessDepthElement = accessDepth[i];

                if (i == 0) {
                    field = clazz.getDeclaredField(accessDepthElement);
                } else {
                    if (!fieldTypeName.startsWith(VALID_USER_DEFINED_TYPE_PACKAGE) && !Collection.class.isAssignableFrom(field.getType())) {
                        throw new IllegalArgumentException(NOT_USER_DEFINED_TYPE_MESSAGE_FORMAT.format(new Object[]{fieldTypeName, paramAccessor}));
                    }

                    // TODO: Handle the case when the field is a collection using ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]
                    field = field.getType().getDeclaredField(accessDepthElement);
                }
                fieldTypeName = field.getType().getName();
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("The field %s does not exist in the class %s".formatted(paramAccessor, clazz.getName()));
            }
        }

        if (!Objects.isNull(fieldTypeName) && fieldTypeName.startsWith(VALID_USER_DEFINED_TYPE_PACKAGE) && !field.getType().isEnum()) {
            throw new IllegalArgumentException("Complex objects cannot be used as search criteria");
        }
    }

    public static String getIdColumnName(Class<?> clazz) {
        Objects.requireNonNull(clazz);

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }

        throw new IllegalArgumentException("No id field found");
    }
}
