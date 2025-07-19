package co.com.juanjogv.lms.domain.repository;

import co.com.juanjogv.lms.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    void save(Author author);

    Optional<Author>  findByName(String name);

    long count();

    void saveAll(List<Author> authors);

    List<Author> findAll();

}
