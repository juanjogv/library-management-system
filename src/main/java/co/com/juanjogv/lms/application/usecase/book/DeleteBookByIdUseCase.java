package co.com.juanjogv.lms.application.usecase.book;


import java.util.UUID;

public interface DeleteBookByIdUseCase {

    void handle(UUID id);

}
