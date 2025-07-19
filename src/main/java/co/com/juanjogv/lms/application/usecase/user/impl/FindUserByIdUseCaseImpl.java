package co.com.juanjogv.lms.application.usecase.user.impl;

import co.com.juanjogv.lms.application.dto.user.FindUserByIdResponse;
import co.com.juanjogv.lms.application.usecase.user.FindUserByIdUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.model.User;
import co.com.juanjogv.lms.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public FindUserByIdResponse handle(UUID userId) {
        return userRepository.findById(userId)
                .map(this::toFindUserByIdResponse)
                .orElseThrow(() -> new NoSuchElementException("Cannot found a User with id %s".formatted(userId)));
    }

    private FindUserByIdResponse toFindUserByIdResponse(User user) {
        return new FindUserByIdResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
