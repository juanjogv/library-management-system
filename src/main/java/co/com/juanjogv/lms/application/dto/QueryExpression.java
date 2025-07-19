package co.com.juanjogv.lms.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryExpression {
    private String parameter;
    private QueryCondition condition;
    private String value;
}
