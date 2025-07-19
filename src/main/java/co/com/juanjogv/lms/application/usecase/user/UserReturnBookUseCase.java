package co.com.juanjogv.lms.application.usecase.user;

import java.util.UUID;

public interface UserReturnBookUseCase {
    void  handle(UUID userId, UUID bookId);
}
