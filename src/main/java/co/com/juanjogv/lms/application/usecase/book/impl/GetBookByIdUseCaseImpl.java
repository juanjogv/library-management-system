package co.com.juanjogv.lms.application.usecase.book.impl;

import co.com.juanjogv.lms.application.dto.book.GetBookByIdResponse;
import co.com.juanjogv.lms.application.usecase.book.GetBookByIdUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.model.Book;
import co.com.juanjogv.lms.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GetBookByIdUseCaseImpl implements GetBookByIdUseCase {

    private final BookRepository bookRepository;

    @Override
    public GetBookByIdResponse handle(UUID id) {
        return bookRepository.findById(id)
                .map(this::toGetBookByIdResponse)
                .orElseThrow(() -> new NoSuchElementException("Cannot found a book with id %s".formatted(id)));
    }

    public GetBookByIdResponse toGetBookByIdResponse(Book book){
        return new GetBookByIdResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAvailability(),
                book.getAuthor().getName()
        );
    }
}
