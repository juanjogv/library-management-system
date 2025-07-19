package co.com.juanjogv.lms.application.usecase.user;

import co.com.juanjogv.lms.application.dto.Page;
import co.com.juanjogv.lms.application.dto.Pageable;
import co.com.juanjogv.lms.application.dto.user.FindAllUsersResponse;

public interface FindAllUsersUseCase {
    Page<FindAllUsersResponse> handle(Pageable pageable);
}
