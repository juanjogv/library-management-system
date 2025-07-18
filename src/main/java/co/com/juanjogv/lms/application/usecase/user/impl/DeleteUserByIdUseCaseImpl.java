package co.com.juanjogv.lms.application.usecase.user.impl;

import co.com.juanjogv.lms.application.usecase.user.DeleteUserByIdUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DeleteUserByIdUseCaseImpl implements DeleteUserByIdUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @CacheEvict(cacheNames = "users", allEntries = true)
    public void handle(UUID userId) {
        userRepository.delete(userId);
    }
}
