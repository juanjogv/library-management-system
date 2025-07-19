package co.com.juanjogv.lms.application.usecase.authentication.impl;

import co.com.juanjogv.lms.application.dto.authentication.AuthenticationResponse;
import co.com.juanjogv.lms.application.dto.authentication.LoginRequest;
import co.com.juanjogv.lms.application.service.JwtService;
import co.com.juanjogv.lms.application.usecase.authentication.UserLoginUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@UseCase
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class UserLoginUseCaseImpl implements UserLoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse handle(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        final var user = userRepository.findByEmail(request.email())
                .orElseThrow();

        final var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }
}
