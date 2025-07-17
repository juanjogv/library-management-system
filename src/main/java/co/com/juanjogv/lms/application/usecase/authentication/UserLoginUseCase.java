package co.com.juanjogv.lms.application.usecase.authentication;

import co.com.juanjogv.lms.application.dto.authentication.AuthenticationResponse;
import co.com.juanjogv.lms.application.dto.authentication.LoginRequest;

public interface UserLoginUseCase {
    AuthenticationResponse handle(LoginRequest request);
}
