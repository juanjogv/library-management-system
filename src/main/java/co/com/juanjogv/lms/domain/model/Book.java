package co.com.juanjogv.lms.domain.model;

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
public class Book {

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

    @OneToMany(mappedBy = "book")
    private List<BorrowingRecord> borrowingRecord;

    public void borrowBook(UUID userId){

        if (!BookAvailability.AVAILABLE.equals(this.availability)) {
            throw new IllegalStateException("The book is not available.");
        }

        final var newBorrowingPetition = BorrowingRecord.builder()
                .id(new BorrowingRecordKey(this.id, userId))
                .borrowDate(OffsetDateTime.now())
                .build();

        this.borrowingRecord.add(newBorrowingPetition);
    }

    public void returnBook(){
        this.setAvailability(BookAvailability.AVAILABLE);
    }
}
