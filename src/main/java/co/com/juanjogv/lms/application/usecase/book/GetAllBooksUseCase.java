package co.com.juanjogv.lms.application.usecase.book;

import co.com.juanjogv.lms.application.dto.Page;
import co.com.juanjogv.lms.application.dto.Pageable;
import co.com.juanjogv.lms.application.dto.book.GetAllBooksResponse;

public interface GetAllBooksUseCase {

    Page<GetAllBooksResponse> handle(Pageable pageable);

}
