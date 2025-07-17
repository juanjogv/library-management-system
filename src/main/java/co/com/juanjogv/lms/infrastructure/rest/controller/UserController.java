package co.com.juanjogv.lms.infrastructure.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management and interaction operations")
public class UserController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Get all users",
        description = "Retrieves a list of all users registered in the system."
    )
    public ResponseEntity<Void> getAllUsers(){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Get user by ID",
        description = "Retrieves detailed information for a specific user given their unique identifier (UUID)."
    )
    public ResponseEntity<Void> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Delete user by ID",
        description = "Permanently deletes a user from the system using their unique identifier (UUID)."
    )
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping(path = "/{userId}/books")
    @Operation(
        summary = "List books borrowed by a user",
        description = "Returns the list of books currently borrowed by the user identified by their UUID."
    )
    public ResponseEntity<Void> getBorrowedBooks(@PathVariable UUID userId){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping(path = "/{userId}/borrow/books/{bookId}")
    @Operation(
        summary = "Borrow a book for a user",
        description = "Assigns a specific book to a user, marking it as borrowed. Requires both the user and book identifiers."
    )
    public ResponseEntity<Void> borrowBook(@PathVariable UUID userId, @PathVariable UUID bookId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping(path = "/{userId}/return/books/{bookId}")
    @Operation(
        summary = "Return a borrowed book",
        description = "Registers the return of a book previously borrowed by a user. Requires both the user and book identifiers."
    )
    public ResponseEntity<Void> returnBook(@PathVariable UUID userId, @PathVariable UUID bookId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
