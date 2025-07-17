package co.com.juanjogv.lms.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public enum QueryCondition {

    EQUALS("="),
    NOT_EQUALS("!="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUALS(">="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUALS("<="),
    LIKE("LIKE"),
    NOT_LIKE("NOT LIKE"),
    IN("IN"),
    NOT_IN("NOT IN"),
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL"),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN");

    private final @NotNull String value;

    @JsonCreator
    public static @NotNull QueryCondition fromText(@NotNull String text) {
        if (Objects.isNull(text) || text.isBlank()) throw new IllegalArgumentException("QueryCondition \"%s\" cannot be null or empty".formatted(text));

        for (QueryCondition queryCondition : values()) {
            if (text.equalsIgnoreCase(queryCondition.value) || text.equalsIgnoreCase(queryCondition.name())) {
                return queryCondition;
            }
        }
        throw new IllegalArgumentException("QueryCondition \"%s\" does not exist".formatted(text));
    }

    public String value() {
        return value;
    }
}
