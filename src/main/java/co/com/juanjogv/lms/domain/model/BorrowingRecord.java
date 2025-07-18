package co.com.juanjogv.lms.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "borrowing_record")
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 8794553293229065546L;

    @EmbeddedId
    private BorrowingRecordKey id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "borrowDate")
    private OffsetDateTime borrowDate;

    @Column(name = "dueDate")
    private OffsetDateTime dueDate;

    @Column(name = "returnedDate")
    private OffsetDateTime returnedDate;

}
