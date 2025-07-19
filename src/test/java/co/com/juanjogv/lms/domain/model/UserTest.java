package co.com.juanjogv.lms.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserTest {

    private User user;
    private List<BorrowingRecord> borrowingRecords;
    private UUID bookId1;
    private UUID bookId2;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        bookId1 = UUID.randomUUID();
        bookId2 = UUID.randomUUID();
        book1 = mock(Book.class);
        when(book1.getId()).thenReturn(bookId1);
        book2 = mock(Book.class);
        when(book2.getId()).thenReturn(bookId2);
        borrowingRecords = new ArrayList<>();
        user = User.builder()
                .id(UUID.randomUUID())
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .borrowingRecord(borrowingRecords)
                .build();
    }

    @Test
    void exceedsMaximumNumberOfBooks_shouldReturnFalseWhenBelowLimit() {
        borrowingRecords.add(BorrowingRecord.builder().book(book1).user(user).borrowDate(LocalDate.now()).build());
        assertFalse(user.exceedsMaximumNumberOfBooks());
    }

    @Test
    void exceedsMaximumNumberOfBooks_shouldReturnTrueWhenAtLimit() {
        borrowingRecords.add(BorrowingRecord.builder().book(book1).user(user).borrowDate(LocalDate.now()).build());
        borrowingRecords.add(BorrowingRecord.builder().book(book2).user(user).borrowDate(LocalDate.now()).build());
        assertTrue(user.exceedsMaximumNumberOfBooks());
    }

    @Test
    void exceedsMaximumNumberOfBooks_shouldIgnoreReturnedBooks() {
        BorrowingRecord borrowingRecord = BorrowingRecord.builder().book(book1).user(user).borrowDate(LocalDate.now()).build();
        borrowingRecord.setReturnedDate(LocalDate.now());
        borrowingRecords.add(borrowingRecord);
        assertFalse(user.exceedsMaximumNumberOfBooks());
    }

    @Test
    void hasBook_shouldReturnTrueIfUserHasBook() {
        borrowingRecords.add(BorrowingRecord.builder().book(book1).user(user).borrowDate(LocalDate.now()).build());
        assertTrue(user.hasBook(bookId1));
    }

    @Test
    void hasBook_shouldReturnFalseIfUserDoesNotHaveBook() {
        borrowingRecords.add(BorrowingRecord.builder().book(book1).user(user).borrowDate(LocalDate.now()).build());
        assertFalse(user.hasBook(bookId2));
    }

    @Test
    void returnBook_shouldSetReturnedDateAndCallBookReturnBook() {
        BorrowingRecord borrowingRecord = spy(BorrowingRecord.builder().book(book1).user(user).borrowDate(LocalDate.now()).build());
        borrowingRecords.add(borrowingRecord);
        doNothing().when(book1).returnBook();
        user.returnBook(bookId1);
        assertNotNull(borrowingRecord.getReturnedDate());
        verify(book1, times(1)).returnBook();
    }

    @Test
    void getAuthorities_shouldReturnRoleAuthority() {
        assertEquals(List.of(new SimpleGrantedAuthority("USER")), user.getAuthorities());
    }

    @Test
    void getUsername_shouldReturnEmail() {
        assertEquals("test@example.com", user.getUsername());
    }
} 