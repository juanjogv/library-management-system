package co.com.juanjogv.lms.application.service;

import co.com.juanjogv.lms.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotificationService {

    private final UserRepository userRepository;

    public void notifyOverdueBooks() {

        final var users = userRepository.findUsersWithOverdueBooks();

        users.forEach(user -> log.info("The user {} has an overdue book, so an email will be sent to the address {} to return the book {}", user.getUserName(), user.getUserEmail(), user.getBookTitle()));
    }
}
