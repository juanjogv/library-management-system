package co.com.juanjogv.lms.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 4684313529681812179L;

    public static final int MAXIMUM_BORROWABLE_BOOKS = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability", nullable = false)
    private BookAvailability availability;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BorrowingRecord> borrowingRecord;

    public void borrowBook(User user) {

        if (!BookAvailability.AVAILABLE.equals(this.availability)) {
            throw new IllegalStateException("The book is not available.");
        }

        final var newBorrowingPetition = BorrowingRecord.builder()
                .borrowDate(OffsetDateTime.now())
                .book(this)
                .user(user)
                .build();

        this.borrowingRecord.add(newBorrowingPetition);
        this.setAvailability(BookAvailability.BORROWED);
    }

    public void returnBook() {
        this.setAvailability(BookAvailability.AVAILABLE);
    }
}
