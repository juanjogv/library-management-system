package co.com.juanjogv.lms.application.dto;

import java.util.List;

public record Pageable(
        int page,
        int size,
        List<QueryExpression> queryExpressions,
        List<Sort> sortList
) {}
