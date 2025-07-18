package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.Role;
import co.com.juanjogv.lms.domain.model.User;
import co.com.juanjogv.lms.domain.projection.FindBorrowingRecordByUserIdProjection;
import co.com.juanjogv.lms.domain.projection.FindCurrentBorrowedBooksByUserIdProjection;
import co.com.juanjogv.lms.domain.projection.FindUsersWithOverdueBooksProjection;
import co.com.juanjogv.lms.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserRepositoryImpl implements UserRepository {

    @Getter
    private final  EntityManager entityManager;
    private final UserJpaRepository userJpaRepository;

    @Override
    public List<FindBorrowingRecordByUserIdProjection> findBorrowingRecordByUserId(UUID userId) {
        return userJpaRepository.findBorrowingRecordByUserId(userId);
    }

    @Override
    public List<FindCurrentBorrowedBooksByUserIdProjection> findCurrentBorrowedBooksByUserId(UUID userId) {
        return userJpaRepository.findCurrentBorrowedBooksByUserId(userId);
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public void saveAll(List<User> user) {
        userJpaRepository.saveAll(user);
    }

    @Override
    public void delete(UUID id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public List<User> findByRole(Role role) {
        return userJpaRepository.findByRole(role);
    }

    @Override
    public List<FindUsersWithOverdueBooksProjection> findUsersWithOverdueBooks() {
        return userJpaRepository.findUsersWithOverdueBooks();
    }
}
