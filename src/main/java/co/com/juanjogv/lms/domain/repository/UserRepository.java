package co.com.juanjogv.lms.domain.repository;

import co.com.juanjogv.lms.domain.model.User;
import co.com.juanjogv.lms.domain.projection.FindBorrowingRecordByUserIdProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends HibernateCustomPagingAndSortingRepository<User, UUID> {

    List<FindBorrowingRecordByUserIdProjection> findBorrowingRecordByUserId(UUID userId);

    void save(User user);

    void delete(UUID id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findById(UUID id);

    @Override
    default Class<User> getEntityClass() {
        return User.class;
    }
}
