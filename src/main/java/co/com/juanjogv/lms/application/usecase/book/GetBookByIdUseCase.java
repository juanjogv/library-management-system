package co.com.juanjogv.lms.application.usecase.book;

import co.com.juanjogv.lms.application.dto.book.GetBookByIdResponse;

import java.util.UUID;

public interface GetBookByIdUseCase {

    GetBookByIdResponse handle(UUID id);

}
