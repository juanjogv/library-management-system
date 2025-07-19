package co.com.juanjogv.lms.application.dto.user;

import java.util.List;
import java.util.UUID;

public record FindBorrowedBooksByUserIdResponse(
        List<Book> books
) {
    public record Book(
            UUID id,
            String title,
            String authorName,
            String isbn,
            boolean hasTheBook
    ) {}
}
