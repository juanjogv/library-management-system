package co.com.juanjogv.lms.application.usecase.authentication.impl;

import co.com.juanjogv.lms.application.dto.authentication.AuthenticationResponse;
import co.com.juanjogv.lms.application.dto.authentication.RegisterRequest;
import co.com.juanjogv.lms.application.service.JwtService;
import co.com.juanjogv.lms.application.usecase.authentication.UserRegisterUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.model.Role;
import co.com.juanjogv.lms.domain.model.User;
import co.com.juanjogv.lms.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserRegisterUseCaseImpl implements UserRegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public AuthenticationResponse handle(RegisterRequest request) {

        final var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        final var jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }
}
