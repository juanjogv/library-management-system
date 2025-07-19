package co.com.juanjogv.lms.infrastructure.rest.controller;

import co.com.juanjogv.lms.application.dto.Page;
import co.com.juanjogv.lms.application.dto.Pageable;
import co.com.juanjogv.lms.application.dto.user.FindAllUsersResponse;
import co.com.juanjogv.lms.application.dto.user.FindBorrowedBooksByUserIdResponse;
import co.com.juanjogv.lms.application.dto.user.FindCurrentBorrowedBooksByUserIdResponse;
import co.com.juanjogv.lms.application.dto.user.FindUserByIdResponse;
import co.com.juanjogv.lms.application.service.JwtService;
import co.com.juanjogv.lms.application.service.PagingService;
import co.com.juanjogv.lms.application.usecase.user.DeleteUserByIdUseCase;
import co.com.juanjogv.lms.application.usecase.user.FindAllUsersUseCase;
import co.com.juanjogv.lms.application.usecase.user.FindBorrowedBooksByUserId;
import co.com.juanjogv.lms.application.usecase.user.FindCurrentBorrowedBooksByUserIdUseCase;
import co.com.juanjogv.lms.application.usecase.user.FindUserByIdUseCase;
import co.com.juanjogv.lms.application.usecase.user.UserBorrowBookUseCase;
import co.com.juanjogv.lms.application.usecase.user.UserReturnBookUseCase;
import co.com.juanjogv.lms.domain.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagingService pagingService;
    @MockitoBean
    private FindAllUsersUseCase findAllUsersUseCase;
    @MockitoBean
    private FindUserByIdUseCase findUserByIdUseCase;
    @MockitoBean
    private DeleteUserByIdUseCase deleteUserByIdUseCase;
    @MockitoBean
    private FindBorrowedBooksByUserId findBorrowedBooksByUserIdUseCase;
    @MockitoBean
    private FindCurrentBorrowedBooksByUserIdUseCase findCurrentBorrowedBooksByUserIdUseCase;
    @MockitoBean
    private UserBorrowBookUseCase userBorrowBookUseCase;
    @MockitoBean
    private UserReturnBookUseCase userReturnBookUseCase;
    @MockitoBean
    private JwtService jwtService;

    @Test
    @DisplayName("GET /api/v1/users returns 200 with pageable results")
    void getAllUsers() throws Exception {
        // Given

        Pageable pageable = new Pageable(0, 10, new ArrayList<>(), new ArrayList<>());
        List<FindAllUsersResponse> users = List.of(
                new FindAllUsersResponse(UUID.randomUUID(), "John Doe", "john@example.com"),
                new FindAllUsersResponse(UUID.randomUUID(), "Jane Smith", "jane@example.com")
        );
        Page<FindAllUsersResponse> page = new Page<>(users, 2, 1, false, false);
        
        when(pagingService.mapToPageable(any(Map.class))).thenReturn(pageable);
        when(findAllUsersUseCase.handle(pageable)).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    @DisplayName("GET /api/v1/users/{userId} returns 200 with user details")
    void getUserById() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        FindUserByIdResponse response = new FindUserByIdResponse(userId, "John Doe", "john@example.com", Role.USER);
        
        when(findUserByIdUseCase.handle(userId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{userId} returns 202")
    void deleteUserById() throws Exception {
        UUID userId = UUID.randomUUID();
        doNothing().when(deleteUserByIdUseCase).handle(userId);
        mockMvc.perform(delete("/api/v1/users/" + userId))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("GET /api/v1/users/{userId}/books returns 200 with borrowed books")
    void getBorrowedBooks() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        List<FindBorrowedBooksByUserIdResponse.Book> books = List.of(
                new FindBorrowedBooksByUserIdResponse.Book(UUID.randomUUID(), "Test Book 1", "Author 1", "1234567890123", true),
                new FindBorrowedBooksByUserIdResponse.Book(UUID.randomUUID(), "Test Book 2", "Author 2", "1234567890124", false)
        );
        FindBorrowedBooksByUserIdResponse response = new FindBorrowedBooksByUserIdResponse(books);
        
        when(findBorrowedBooksByUserIdUseCase.handle(userId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/users/" + userId + "/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/v1/users/{userId}/books/borrowed returns 200 with current borrowed books")
    void getCurrentBorrowedBooks() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        List<FindCurrentBorrowedBooksByUserIdResponse.Book> books = List.of(
                new FindCurrentBorrowedBooksByUserIdResponse.Book(UUID.randomUUID(), "Current Book 1", "Author 1", "1234567890123"),
                new FindCurrentBorrowedBooksByUserIdResponse.Book(UUID.randomUUID(), "Current Book 2", "Author 2", "1234567890124")
        );
        FindCurrentBorrowedBooksByUserIdResponse response = new FindCurrentBorrowedBooksByUserIdResponse(books);
        
        when(findCurrentBorrowedBooksByUserIdUseCase.handle(userId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/users/" + userId + "/books/borrowed"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books.length()").value(2));
    }

    @Test
    @DisplayName("PUT /api/v1/users/{userId}/borrow/books/{bookId} returns 202")
    void borrowBook() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        doNothing().when(userBorrowBookUseCase).handle(userId, bookId);
        mockMvc.perform(put("/api/v1/users/" + userId + "/borrow/books/" + bookId))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("PUT /api/v1/users/{userId}/return/books/{bookId} returns 202")
    void returnBook() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        doNothing().when(userReturnBookUseCase).handle(userId, bookId);

        // When & Then
        mockMvc.perform(put("/api/v1/users/" + userId + "/return/books/" + bookId))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("GET /api/v1/users/{userId} returns 500 when user not found")
    void getUserByIdNotFound() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        when(findUserByIdUseCase.handle(userId)).thenThrow(new RuntimeException("User not found"));

        // When & Then
        mockMvc.perform(get("/api/v1/users/" + userId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("GET /api/v1/users with pagination parameters returns 200")
    void getAllUsersWithPagination() throws Exception {
        // Given
        Pageable pageable = new Pageable(1, 5, new ArrayList<>(), new ArrayList<>());
        List<FindAllUsersResponse> users = List.of(
                new FindAllUsersResponse(UUID.randomUUID(), "User 1", "user1@example.com"),
                new FindAllUsersResponse(UUID.randomUUID(), "User 2", "user2@example.com")
        );
        Page<FindAllUsersResponse> page = new Page<>(users, 2, 2, true, false);
        
        when(pagingService.mapToPageable(any(Map.class))).thenReturn(pageable);
        when(findAllUsersUseCase.handle(pageable)).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/users")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.hasPrevious").value(true))
                .andExpect(jsonPath("$.hasNext").value(false));
    }

    @Test
    @DisplayName("PUT /api/v1/users/{userId}/borrow/books/{bookId} returns 500 when borrowing fails")
    void borrowBookFailure() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        doThrow(new RuntimeException("Borrowing failed")).when(userBorrowBookUseCase).handle(userId, bookId);

        // When & Then
        mockMvc.perform(put("/api/v1/users/" + userId + "/borrow/books/" + bookId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{userId} returns 500 when deletion fails")
    void deleteUserByIdFailure() throws Exception {
        // Given
        UUID userId = UUID.randomUUID();
        doThrow(new RuntimeException("Deletion failed")).when(deleteUserByIdUseCase).handle(userId);

        // When & Then
        mockMvc.perform(delete("/api/v1/users/" + userId))
                .andExpect(status().isInternalServerError());
    }
}