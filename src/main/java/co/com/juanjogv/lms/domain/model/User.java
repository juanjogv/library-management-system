package co.com.juanjogv.lms.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`user`")
public class User implements UserDetails {

    @Serial
    private static final long serialVersionUID = 4050623983298544678L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", unique = true)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<BorrowingRecord> borrowingRecord;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    public boolean exceedsMaximumNumberOfBooks() {
        return getCurrentBooks().count() >= Book.MAXIMUM_BORROWABLE_BOOKS;
    }

    private Stream<BorrowingRecord> getCurrentBooks() {
        return borrowingRecord.stream()
                .filter(e -> Objects.isNull(e.getReturnedDate()));
    }

    public boolean hasBook(UUID bookId) {
        return getCurrentBooks().filter(e -> e.getId().getBookId().equals(bookId))
                .findFirst()
                .isEmpty();
    }

    public void returnBook(UUID bookId) {
        getCurrentBooks()
                .filter(e -> e.getId().getBookId().equals(bookId))
                .findFirst()
                .ifPresent(bookRecord -> {
                    bookRecord.setReturnedDate(OffsetDateTime.now());

                    final var book = bookRecord.getBook();
                    book.returnBook();
                });
    }
}
