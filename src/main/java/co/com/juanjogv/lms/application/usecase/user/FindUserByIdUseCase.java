package co.com.juanjogv.lms.application.usecase.user;

import co.com.juanjogv.lms.application.dto.user.FindUserByIdResponse;

import java.util.UUID;

public interface FindUserByIdUseCase {
    FindUserByIdResponse handle(UUID userId);
}
