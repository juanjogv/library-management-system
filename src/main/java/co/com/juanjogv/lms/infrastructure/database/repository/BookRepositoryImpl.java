package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.Book;
import co.com.juanjogv.lms.domain.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    EntityManager entityManager;

    private final BookJpaRepository bookJpaRepository;

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
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<Book> getEntityClass() {
        return Book.class;
    }
}
