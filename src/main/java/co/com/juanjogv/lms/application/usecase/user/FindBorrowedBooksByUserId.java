package co.com.juanjogv.lms.application.usecase.user;

import co.com.juanjogv.lms.application.dto.user.FindBorrowedBooksByUserIdResponse;

import java.util.UUID;

public interface FindBorrowedBooksByUserId {
    FindBorrowedBooksByUserIdResponse handle(UUID userId);
}
