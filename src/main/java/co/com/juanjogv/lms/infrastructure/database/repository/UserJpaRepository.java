package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.User;
import co.com.juanjogv.lms.domain.projection.FindBorrowingRecordByUserIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT bk.title AS bookTitle, bk.isbn as bookIsbn, au.name as authorName FROM User us INNER JOIN us.borrowingRecord br INNER JOIN br.book bk INNER JOIN bk.author au WHERE us.id = :userId")
    List<FindBorrowingRecordByUserIdProjection> findBorrowingRecordByUserId(UUID userId);
}
