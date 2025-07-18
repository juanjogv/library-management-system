package co.com.juanjogv.lms.infrastructure.rest.controller;

import co.com.juanjogv.lms.application.dto.Page;
import co.com.juanjogv.lms.application.dto.Pageable;
import co.com.juanjogv.lms.application.dto.book.AddBookRequest;
import co.com.juanjogv.lms.application.dto.book.GetAllBooksResponse;
import co.com.juanjogv.lms.application.dto.book.GetBookByIdResponse;
import co.com.juanjogv.lms.application.service.PagingService;
import co.com.juanjogv.lms.application.usecase.book.AddBookUseCase;
import co.com.juanjogv.lms.application.usecase.book.DeleteBookByIdUseCase;
import co.com.juanjogv.lms.application.usecase.book.GetAllBooksUseCase;
import co.com.juanjogv.lms.application.usecase.book.GetBookByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Book management operations")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookController {

    private final PagingService pagingService;
    private final GetAllBooksUseCase getAllBooksUseCase;
    private final AddBookUseCase addBookUseCase;
    private final GetBookByIdUseCase getBookByIdUseCase;
    private final DeleteBookByIdUseCase deleteBookByIdUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all books", description = "Retrieve all books")
    public ResponseEntity<Page<GetAllBooksResponse>> getAllBooks(@RequestParam Map<String, String> searchParams) {
        Pageable pageable = pagingService.mapToPageable(searchParams);
        return ResponseEntity.ok(getAllBooksUseCase.handle(pageable));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create book", description = "Add a new book")
    public ResponseEntity<Void> addBook(@Valid @RequestBody AddBookRequest addBookRequest) {
        UUID uuid = addBookUseCase.handle(addBookRequest);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(uuid)
                        .toUri()
        ).build();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get book by ID", description = "Retrieve a specific book by ID")
    public ResponseEntity<GetBookByIdResponse> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(getBookByIdUseCase.handle(id));
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete book by ID", description = "Deletes a specific book by its ID")
    public ResponseEntity<Void> deleteBookById(@PathVariable UUID id) {
        deleteBookByIdUseCase.handle(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
