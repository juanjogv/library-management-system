package co.com.juanjogv.lms.domain.repository;

import co.com.juanjogv.lms.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

}
