package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.Book;
import co.com.juanjogv.lms.domain.model.BookAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookJpaRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAvailability(BookAvailability availability);
}
