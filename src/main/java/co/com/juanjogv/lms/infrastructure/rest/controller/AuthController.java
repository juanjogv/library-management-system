package co.com.juanjogv.lms.infrastructure.rest.controller;

import co.com.juanjogv.lms.application.dto.AuthenticationResponse;
import co.com.juanjogv.lms.application.dto.LoginRequest;
import co.com.juanjogv.lms.application.dto.RegisterRequest;
import co.com.juanjogv.lms.application.usecase.UserLoginUseCase;
import co.com.juanjogv.lms.application.usecase.UserRegisterUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthController {

    private final UserLoginUseCase userLoginUseCase;
    private final UserRegisterUseCase userRegisterUseCase;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest request) {
        final var response = userLoginUseCase.handle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        final var response = userRegisterUseCase.handle(request);
        return ResponseEntity.ok(response);
    }
}
