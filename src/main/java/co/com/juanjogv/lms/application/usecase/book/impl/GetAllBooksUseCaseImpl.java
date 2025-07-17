package co.com.juanjogv.lms.application.usecase.book.impl;

import co.com.juanjogv.lms.application.dto.Page;
import co.com.juanjogv.lms.application.dto.Pageable;
import co.com.juanjogv.lms.application.dto.book.GetAllBooksResponse;
import co.com.juanjogv.lms.application.usecase.book.GetAllBooksUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetAllBooksUseCaseImpl implements GetAllBooksUseCase {

    private final BookRepository bookRepository;

    @Override
    public Page<GetAllBooksResponse> handle(Pageable pageable) {
        return bookRepository.findAll(pageable, GetAllBooksResponse.class);
    }
}
