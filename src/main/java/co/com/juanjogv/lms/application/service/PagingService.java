package co.com.juanjogv.lms.application.service;

import co.com.juanjogv.lms.application.dto.Pageable;
import co.com.juanjogv.lms.application.dto.QueryExpression;
import co.com.juanjogv.lms.application.dto.Sort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PagingService {

    private final ObjectMapper mapper;

    public Pageable mapToPageable(Map<String, String> queryParams) {

        int page = Integer.parseInt(queryParams.computeIfAbsent("page", s -> "0"));
        int size = Integer.parseInt(queryParams.computeIfAbsent("size", s -> "5"));
        List<QueryExpression> queryExpressions = Optional.ofNullable(queryParams.get("queryExpressions"))
                .map(e -> {
                    try {
                        return mapper.readValue(e, new TypeReference<List<QueryExpression>>() {});
                    } catch (JsonProcessingException ex) {
                        log.error(ex.getMessage(), ex);
                        return null;
                    }
                })
                .orElse(Collections.emptyList());
        List<Sort> sortList = Optional.ofNullable(queryParams.get("sortList"))
                .map(e -> {
                    try {
                        return mapper.readValue(e, new TypeReference<List<Sort>>() {});
                    } catch (JsonProcessingException ex) {
                        log.error(ex.getMessage(), ex);
                        return null;
                    }
                })
                .orElse(List.of(new Sort("id","desc")));

        return new Pageable(
                page,
                size,
                queryExpressions,
                sortList
        );
    }
}
