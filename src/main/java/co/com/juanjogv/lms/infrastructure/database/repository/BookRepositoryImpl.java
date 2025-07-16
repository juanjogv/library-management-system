package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.Book;
import co.com.juanjogv.lms.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository bookJpaRepository;

    @Override
    public void save(Book book) {
        bookJpaRepository.save(book);
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return bookJpaRepository.findById(id);
    }
}
