package co.com.juanjogv.lms.application.usecase.authentication;

import co.com.juanjogv.lms.application.dto.authentication.AuthenticationResponse;
import co.com.juanjogv.lms.application.dto.authentication.RegisterRequest;

public interface UserRegisterUseCase {
    AuthenticationResponse handle(RegisterRequest request);
}
