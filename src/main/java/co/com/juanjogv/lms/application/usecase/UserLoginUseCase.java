package co.com.juanjogv.lms.application.usecase;

import co.com.juanjogv.lms.application.dto.AuthenticationResponse;
import co.com.juanjogv.lms.application.dto.LoginRequest;

public interface UserLoginUseCase {
    AuthenticationResponse handle(LoginRequest request);
}
