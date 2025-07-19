package co.com.juanjogv.lms.application.usecase.user;

import co.com.juanjogv.lms.application.dto.user.FindCurrentBorrowedBooksByUserIdResponse;

import java.util.UUID;

public interface FindCurrentBorrowedBooksByUserIdUseCase {
    FindCurrentBorrowedBooksByUserIdResponse handle(UUID userId);
}
