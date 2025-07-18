package co.com.juanjogv.lms.application.usecase.book.impl;

import co.com.juanjogv.lms.application.dto.Page;
import co.com.juanjogv.lms.application.dto.Pageable;
import co.com.juanjogv.lms.application.dto.book.GetAllBooksResponse;
import co.com.juanjogv.lms.application.usecase.book.GetAllBooksUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetAllBooksUseCaseImpl implements GetAllBooksUseCase {

    private final BookRepository bookRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Cacheable(
            cacheNames = "users",
            condition = "#pageable.queryExpressions().isEmpty() && #pageable.hasDefaultSort()",
            unless = "#result.content().size() > 20"
    )
    public Page<GetAllBooksResponse> handle(Pageable pageable) {
        return bookRepository.findAll(pageable, GetAllBooksResponse.class);
    }
}
