package co.com.juanjogv.lms.application.usecase.user.impl;

import co.com.juanjogv.lms.application.usecase.user.UserReturnBookUseCase;
import co.com.juanjogv.lms.common.UseCase;
import co.com.juanjogv.lms.domain.repository.BookRepository;
import co.com.juanjogv.lms.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserReturnBookUseCaseImpl implements UserReturnBookUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handle(UUID userId, UUID bookId) {

        final var user =userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id %s does not exist.".formatted(userId)));

        if (!user.hasBook(bookId)) {
            throw new IllegalStateException("The specified user does not have the specified book");
        }

        user.returnBook(bookId);
        userRepository.save(user);
    }
}
