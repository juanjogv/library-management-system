package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.Book;
import co.com.juanjogv.lms.domain.model.BookAvailability;
import co.com.juanjogv.lms.domain.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(Book book) {
        bookJpaRepository.save(book);
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return bookJpaRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        bookJpaRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Book> books) {
        bookJpaRepository.saveAll(books);
    }

    @Override
    public List<Book> findByAvailability(BookAvailability bookAvailability) {
        return bookJpaRepository.findByAvailability(bookAvailability);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Book> getEntityClass() {
        return Book.class;
    }
}
