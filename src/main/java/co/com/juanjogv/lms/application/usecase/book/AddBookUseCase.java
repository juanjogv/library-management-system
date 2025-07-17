package co.com.juanjogv.lms.application.usecase.book;

import co.com.juanjogv.lms.application.dto.book.AddBookRequest;

import java.util.UUID;

public interface AddBookUseCase {

    UUID handle(AddBookRequest addBookRequest);

}
