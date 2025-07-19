package co.com.juanjogv.lms.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookTest {

    private Book book;
    private Author author;
    private User user;

    @BeforeEach
    void setUp() {
        author = mock(Author.class);
        user = mock(User.class);
        book = Book.builder()
                .id(UUID.randomUUID())
                .title("Test Book")
                .isbn("1234567890")
                .availability(BookAvailability.AVAILABLE)
                .author(author)
                .borrowingRecord(new ArrayList<>())
                .build();
    }

    @Test
    void borrowBook_shouldAddBorrowingRecordAndSetAvailabilityToBorrowed() {
        book.borrowBook(user);
        assertEquals(BookAvailability.BORROWED, book.getAvailability());
        assertEquals(1, book.getBorrowingRecord().size());
        BorrowingRecord borrowingRecord = book.getBorrowingRecord().getFirst();
        assertEquals(book, borrowingRecord.getBook());
        assertEquals(user, borrowingRecord.getUser());
        assertNotNull(borrowingRecord.getBorrowDate());
    }

    @Test
    void borrowBook_shouldThrowExceptionIfBookNotAvailable() {
        book.setAvailability(BookAvailability.BORROWED);
        Exception exception = assertThrows(IllegalStateException.class, () -> book.borrowBook(user));
        assertEquals("The book is not available.", exception.getMessage());
    }

    @Test
    void returnBook_shouldSetAvailabilityToAvailable() {
        book.setAvailability(BookAvailability.BORROWED);
        book.returnBook();
        assertEquals(BookAvailability.AVAILABLE, book.getAvailability());
    }
} 