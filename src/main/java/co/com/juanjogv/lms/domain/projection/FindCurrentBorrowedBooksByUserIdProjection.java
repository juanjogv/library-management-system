package co.com.juanjogv.lms.domain.projection;

import java.util.UUID;

public interface FindCurrentBorrowedBooksByUserIdProjection {

    UUID getId();

    String getBookTitle();

    String getBookIsbn();

    String getAuthorName();

}
