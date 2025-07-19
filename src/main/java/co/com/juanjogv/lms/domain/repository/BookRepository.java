package co.com.juanjogv.lms.domain.repository;

import co.com.juanjogv.lms.domain.model.Book;
import co.com.juanjogv.lms.domain.model.BookAvailability;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends HibernateCustomPagingAndSortingRepository<Book, UUID> {

    void save(Book book);

    Optional<Book> findById(UUID id);

    void deleteById(UUID id);

    void saveAll(List<Book> books);

    List<Book> findByAvailability(BookAvailability bookAvailability);
}
