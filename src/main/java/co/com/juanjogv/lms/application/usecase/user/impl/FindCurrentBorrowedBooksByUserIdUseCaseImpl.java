package co.com.juanjogv.lms.application.usecase.user.impl;

import co.com.juanjogv.lms.application.dto.user.FindCurrentBorrowedBooksByUserIdResponse;
import co.com.juanjogv.lms.application.usecase.user.FindCurrentBorrowedBooksByUserIdUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.projection.FindCurrentBorrowedBooksByUserIdProjection;
import co.com.juanjogv.lms.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FindCurrentBorrowedBooksByUserIdUseCaseImpl implements FindCurrentBorrowedBooksByUserIdUseCase {

    private final UserRepository userRepository;

    @Override
    public FindCurrentBorrowedBooksByUserIdResponse handle(UUID userId) {
        return userRepository.findCurrentBorrowedBooksByUserId(userId).stream()
                .map(this::book)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        FindCurrentBorrowedBooksByUserIdResponse::new
                ));
    }

    private FindCurrentBorrowedBooksByUserIdResponse.Book book(FindCurrentBorrowedBooksByUserIdProjection source) {
        return new FindCurrentBorrowedBooksByUserIdResponse.Book(
                source.getId(),
                source.getBookTitle(),
                source.getAuthorName(),
                source.getBookIsbn()
        );
    }
}
