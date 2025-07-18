package co.com.juanjogv.lms.application.usecase.user;

import java.util.UUID;

public interface DeleteUserByIdUseCase {
    void handle(UUID userId);
}
