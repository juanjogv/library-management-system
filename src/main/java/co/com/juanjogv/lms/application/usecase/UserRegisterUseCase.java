package co.com.juanjogv.lms.application.usecase;

import co.com.juanjogv.lms.application.dto.AuthenticationResponse;
import co.com.juanjogv.lms.application.dto.RegisterRequest;

public interface UserRegisterUseCase {
    AuthenticationResponse handle(RegisterRequest request);
}
