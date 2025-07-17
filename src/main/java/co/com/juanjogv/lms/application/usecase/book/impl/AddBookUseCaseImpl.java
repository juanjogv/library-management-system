package co.com.juanjogv.lms.application.usecase.book.impl;

import co.com.juanjogv.lms.application.dto.book.AddBookRequest;
import co.com.juanjogv.lms.application.service.AuthorService;
import co.com.juanjogv.lms.application.usecase.book.AddBookUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.model.Author;
import co.com.juanjogv.lms.domain.model.Book;
import co.com.juanjogv.lms.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AddBookUseCaseImpl implements AddBookUseCase {

    private final AuthorService authorService;
    private final BookRepository bookRepository;

    @Override
    public UUID handle(AddBookRequest addBookRequest) {

        UUID authorUuid = Optional.ofNullable(addBookRequest.authorId())
                .or(() ->
                        Optional.ofNullable(addBookRequest.author())
                                .map(AddBookRequest.Author::name)
                                .map(authorService::registerAuthor)
                )
                .orElseThrow(IllegalStateException::new);

        Book book = Book.builder()
                .title(addBookRequest.title())
                .isbn(addBookRequest.isbn())
                .availability(addBookRequest.availability())
                .author(Author.fromId(authorUuid))
                .build();

        bookRepository.save(book);

        return book.getId();
    }
}
