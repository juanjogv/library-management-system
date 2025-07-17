package co.com.juanjogv.lms.domain.repository;

import co.com.juanjogv.lms.domain.model.Book;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends HibernateCustomPagingAndSortingRepository<Book, UUID> {

    void save(Book book);

    Optional<Book> findById(UUID id);

    void deleteById(UUID id);

}
