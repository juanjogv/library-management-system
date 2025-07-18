package co.com.juanjogv.lms.application.usecase.user.impl;

import co.com.juanjogv.lms.application.dto.user.FindBorrowedBooksByUserIdResponse;
import co.com.juanjogv.lms.application.usecase.user.FindBorrowedBooksByUserId;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.projection.FindBorrowingRecordByUserIdProjection;
import co.com.juanjogv.lms.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FindBorrowedBooksByUserIdImpl implements FindBorrowedBooksByUserId {

    private final UserRepository userRepository;

    @Override
    public FindBorrowedBooksByUserIdResponse handle(UUID userId) {
        return userRepository.findBorrowingRecordByUserId(userId).stream()
                .map(this::mapToFindBorrowedBooksByUserIdResponse)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        FindBorrowedBooksByUserIdResponse::new
                ));
    }

    private FindBorrowedBooksByUserIdResponse.Book mapToFindBorrowedBooksByUserIdResponse(FindBorrowingRecordByUserIdProjection source) {
        return new FindBorrowedBooksByUserIdResponse.Book(
                source.getBookTitle(),
                source.getAuthorName(),
                source.getBookIsbn()
        );
    }
}
