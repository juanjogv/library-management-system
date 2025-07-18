package co.com.juanjogv.lms.application.dto;

import java.util.List;

public record Pageable(
        int page,
        int size,
        List<QueryExpression> queryExpressions,
        List<Sort> sortList
) {

    public boolean hasDefaultSort() {
        return sortList.size() == 1 && sortList.getFirst().getBy().equals("id") && sortList.getFirst().getDirection().equals("desc");
    }
}
