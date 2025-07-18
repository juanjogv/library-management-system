package co.com.juanjogv.lms.application.dto.user;

import java.util.List;

public record FindBorrowedBooksByUserIdResponse(
        List<Book> books
) {
    public record Book(
            String title,
            String authorName,
            String isbn
    ) {}
}
