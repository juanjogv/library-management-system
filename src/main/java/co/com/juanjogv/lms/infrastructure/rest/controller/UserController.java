package co.com.juanjogv.lms.infrastructure.rest.controller;

import co.com.juanjogv.lms.application.dto.Page;
import co.com.juanjogv.lms.application.dto.Pageable;
import co.com.juanjogv.lms.application.dto.user.FindAllUsersResponse;
import co.com.juanjogv.lms.application.dto.user.FindBorrowedBooksByUserIdResponse;
import co.com.juanjogv.lms.application.dto.user.FindCurrentBorrowedBooksByUserIdResponse;
import co.com.juanjogv.lms.application.dto.user.FindUserByIdResponse;
import co.com.juanjogv.lms.application.service.PagingService;
import co.com.juanjogv.lms.application.usecase.user.DeleteUserByIdUseCase;
import co.com.juanjogv.lms.application.usecase.user.FindAllUsersUseCase;
import co.com.juanjogv.lms.application.usecase.user.FindBorrowedBooksByUserId;
import co.com.juanjogv.lms.application.usecase.user.FindCurrentBorrowedBooksByUserIdUseCase;
import co.com.juanjogv.lms.application.usecase.user.FindUserByIdUseCase;
import co.com.juanjogv.lms.application.usecase.user.UserBorrowBookUseCase;
import co.com.juanjogv.lms.application.usecase.user.UserReturnBookUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management and interaction operations")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final PagingService pagingService;
    private final FindAllUsersUseCase findAllUsersUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final DeleteUserByIdUseCase deleteUserByIdUseCase;
    private final FindBorrowedBooksByUserId findBorrowedBooksByUserIdUseCase;
    private final FindCurrentBorrowedBooksByUserIdUseCase findCurrentBorrowedBooksByUserIdUseCase;
    private final UserBorrowBookUseCase userBorrowBookUseCase;
    private final UserReturnBookUseCase userReturnBookUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all users registered in the system."
    )
    @Secured({"ROLE_LIBRARIAN","ROLE_ADMIN"})
    public ResponseEntity<Page<FindAllUsersResponse>> findAllUsers(@RequestParam Map<String, String> searchParams) {
        Pageable pageable = pagingService.mapToPageable(searchParams);
        return ResponseEntity.ok(findAllUsersUseCase.handle(pageable));
    }

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get user by ID",
            description = "Retrieves detailed information for a specific user given their unique identifier (UUID)."
    )
    @Secured({"ROLE_LIBRARIAN","ROLE_ADMIN"})
    public ResponseEntity<FindUserByIdResponse> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(findUserByIdUseCase.handle(userId));
    }

    @DeleteMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Delete user by ID",
            description = "Permanently deletes a user from the system using their unique identifier (UUID)."
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId) {
        deleteUserByIdUseCase.handle(userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping(path = "/{userId}/books")
    @Operation(
            summary = "List all books borrowed by a user",
            description = "Returns the list of books borrowed by the user id."
    )
    @Secured({"ROLE_USER", "ROLE_LIBRARIAN","ROLE_ADMIN"})
    public ResponseEntity<FindBorrowedBooksByUserIdResponse> getBorrowedBooks(@PathVariable UUID userId) {
        return ResponseEntity.ok(findBorrowedBooksByUserIdUseCase.handle(userId));
    }

    @GetMapping(path = "/{userId}/books/borrowed")
    @Operation(
            summary = "List all current borrowed books by a user",
            description = "Returns the list of current borrowed books borrowed by the user id."
    )
    @Secured({"ROLE_USER", "ROLE_LIBRARIAN","ROLE_ADMIN"})
    public ResponseEntity<FindCurrentBorrowedBooksByUserIdResponse> getCurrentBorrowedBooks(@PathVariable UUID userId) {
        return ResponseEntity.ok(findCurrentBorrowedBooksByUserIdUseCase.handle(userId));
    }

    @PutMapping(path = "/{userId}/borrow/books/{bookId}")
    @Operation(
            summary = "Borrow a book for a user",
            description = "Assigns a specific book to a user, marking it as borrowed. Requires both the user and book identifiers."
    )
    @Secured({"ROLE_LIBRARIAN","ROLE_ADMIN"})
    public ResponseEntity<Void> borrowBook(@PathVariable UUID userId, @PathVariable UUID bookId) {
        userBorrowBookUseCase.handle(userId, bookId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping(path = "/{userId}/return/books/{bookId}")
    @Operation(
            summary = "Return a borrowed book",
            description = "Registers the return of a book previously borrowed by a user. Requires both the user and book identifiers."
    )
    @Secured({"ROLE_LIBRARIAN","ROLE_ADMIN"})
    public ResponseEntity<Void> returnBook(@PathVariable UUID userId, @PathVariable UUID bookId) {
        userReturnBookUseCase.handle(userId, bookId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
