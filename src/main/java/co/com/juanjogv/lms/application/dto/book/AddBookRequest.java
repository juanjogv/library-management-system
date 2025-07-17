package co.com.juanjogv.lms.application.dto.book;

import co.com.juanjogv.lms.domain.model.BookAvailability;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddBookRequest(
        @NotBlank String title,
        @NotBlank String isbn,
        @NotNull BookAvailability availability,
        @Nullable UUID authorId,
        @Nullable Author author
) {

    public record Author(
            @NotBlank String name
    ){}
}
