package co.com.juanjogv.lms.application.usecase.book.impl;

import co.com.juanjogv.lms.application.usecase.book.DeleteBookByIdUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DeleteBookByIdUseCaseImpl implements DeleteBookByIdUseCase {

    private final BookRepository bookRepository;

    @Override
    public void handle(UUID id) {
        bookRepository.deleteById(id);
    }
}
