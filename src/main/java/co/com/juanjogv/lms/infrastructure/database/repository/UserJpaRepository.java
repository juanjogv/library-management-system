package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.Role;
import co.com.juanjogv.lms.domain.model.User;
import co.com.juanjogv.lms.domain.projection.FindBorrowingRecordByUserIdProjection;
import co.com.juanjogv.lms.domain.projection.FindCurrentBorrowedBooksByUserIdProjection;
import co.com.juanjogv.lms.domain.projection.FindUsersWithOverdueBooksProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT bk.id as id, bk.title AS bookTitle, bk.isbn as bookIsbn, au.name as authorName, br.returnedDate as returnedDate FROM User us INNER JOIN us.borrowingRecord br INNER JOIN br.book bk INNER JOIN bk.author au WHERE us.id = :userId")
    List<FindBorrowingRecordByUserIdProjection> findBorrowingRecordByUserId(UUID userId);

    @Query("SELECT bk.id as id, bk.title AS bookTitle, bk.isbn as bookIsbn, au.name as authorName FROM User us INNER JOIN us.borrowingRecord br INNER JOIN br.book bk INNER JOIN bk.author au WHERE us.id = :userId AND br.returnedDate IS NULL")
    List<FindCurrentBorrowedBooksByUserIdProjection> findCurrentBorrowedBooksByUserId(UUID userId);

    @Query("SELECT us.name as userName, us.email AS userEmail, bk.title as bookTitle FROM User us INNER JOIN us.borrowingRecord br INNER JOIN br.book bk WHERE br.returnedDate IS NULL AND br.dueDate < local_date ")
    List<FindUsersWithOverdueBooksProjection> findUsersWithOverdueBooks();

    List<User> findByRole(Role role);
}
