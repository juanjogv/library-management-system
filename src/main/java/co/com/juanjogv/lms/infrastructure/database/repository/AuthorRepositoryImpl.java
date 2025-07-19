package co.com.juanjogv.lms.infrastructure.database.repository;

import co.com.juanjogv.lms.domain.model.Author;
import co.com.juanjogv.lms.domain.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorJpaRepository authorJpaRepository;

    @Override
    public void save(Author author) {
        authorJpaRepository.save(author);
    }

    @Override
    public Optional<Author> findByName(String name) {
        return authorJpaRepository.findByName(name);
    }

    @Override
    public long count() {
        return authorJpaRepository.count();
    }

    @Override
    public void saveAll(List<Author> authors) {
        authorJpaRepository.saveAll(authors);
    }

    @Override
    public List<Author> findAll() {
        return authorJpaRepository.findAll();
    }
}
