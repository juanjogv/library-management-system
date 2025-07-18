package co.com.juanjogv.lms.domain.repository;

import co.com.juanjogv.lms.domain.model.Role;
import co.com.juanjogv.lms.domain.model.User;
import co.com.juanjogv.lms.domain.projection.FindBorrowingRecordByUserIdProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends HibernateCustomPagingAndSortingRepository<User, UUID> {

    @Override
    default Class<User> getEntityClass() {
        return User.class;
    }

    List<FindBorrowingRecordByUserIdProjection> findBorrowingRecordByUserId(UUID userId);

    void save(User user);

    void saveAll(List<User> user);

    void delete(UUID id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findById(UUID id);

    List<User> findByRole(Role role);
}
