package co.com.juanjogv.lms.domain.projection;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface FindBorrowingRecordByUserIdProjection {

    UUID getId();

    String getBookTitle();

    String getBookIsbn();

    String getAuthorName();

    OffsetDateTime getReturnedDate();

}
