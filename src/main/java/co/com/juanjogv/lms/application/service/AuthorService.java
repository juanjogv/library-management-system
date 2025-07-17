package co.com.juanjogv.lms.application.service;

import co.com.juanjogv.lms.domain.model.Author;
import co.com.juanjogv.lms.infrastructure.database.repository.AuthorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class AuthorService {

    private final AuthorJpaRepository authorJpaRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public UUID registerAuthor(String authorName){
        Author author = Author.builder()
                .name(authorName)
                .build();

        authorJpaRepository.save(author);

        return author.getId();
    }
}
