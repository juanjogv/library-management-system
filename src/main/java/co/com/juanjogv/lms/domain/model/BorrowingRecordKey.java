package co.com.juanjogv.lms.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BorrowingRecordKey implements Serializable {

    @Serial
    private static final long serialVersionUID = -7584432700528902193L;

    @Column(name = "book_id")
    private UUID bookId;

    @Column(name = "user_id")
    private UUID userId;

}
