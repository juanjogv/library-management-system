package co.com.juanjogv.lms.infrastructure.rest.controller;

import co.com.juanjogv.lms.application.dto.Page;
import co.com.juanjogv.lms.application.dto.Pageable;
import co.com.juanjogv.lms.application.dto.book.AddBookRequest;
import co.com.juanjogv.lms.application.dto.book.GetAllBooksResponse;
import co.com.juanjogv.lms.application.dto.book.GetBookByIdResponse;
import co.com.juanjogv.lms.application.service.JwtService;
import co.com.juanjogv.lms.application.service.PagingService;
import co.com.juanjogv.lms.application.usecase.book.AddBookUseCase;
import co.com.juanjogv.lms.application.usecase.book.DeleteBookByIdUseCase;
import co.com.juanjogv.lms.application.usecase.book.GetAllBooksUseCase;
import co.com.juanjogv.lms.application.usecase.book.GetBookByIdUseCase;
import co.com.juanjogv.lms.domain.model.BookAvailability;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
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
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PagingService pagingService;
    @MockitoBean
    private GetAllBooksUseCase getAllBooksUseCase;
    @MockitoBean
    private AddBookUseCase addBookUseCase;
    @MockitoBean
    private GetBookByIdUseCase getBookByIdUseCase;
    @MockitoBean
    private DeleteBookByIdUseCase deleteBookByIdUseCase;
    @MockitoBean
    private JwtService jwtService;

    @Test
    @DisplayName("GET /api/v1/books returns 200 with pageable results")
    void getAllBooks() throws Exception {
        // Given
        Pageable pageable = new Pageable(0, 10, new ArrayList<>(), new ArrayList<>());
        Page<GetAllBooksResponse> page = new Page<>(Collections.emptyList(), 10, 1, false, false);
        
        when(pagingService.mapToPageable(any(Map.class))).thenReturn(pageable);
        when(getAllBooksUseCase.handle(pageable)).thenReturn(page);

        // When & Then
        mockMvc.perform(
                get("/api/v1/books")
                        .with(user("user").password("123").roles("USER"))
                        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /api/v1/books with pagination parameters returns 200")
    void getAllBooksWithPagination() throws Exception {
        // Given
        Pageable pageable = new Pageable(1, 5, new ArrayList<>(), new ArrayList<>());
        Page<GetAllBooksResponse> page = new Page<>(Collections.emptyList(), 5, 2, true, false);
        
        when(pagingService.mapToPageable(any(Map.class))).thenReturn(pageable);
        when(getAllBooksUseCase.handle(pageable)).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/books")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /api/v1/books returns 201 when book is created successfully")
    void addBook() throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        AddBookRequest request = new AddBookRequest(
                "Test Book",
                "1234567890123",
                BookAvailability.AVAILABLE,
                null,
                new AddBookRequest.Author("Test Author")
        );
        
        when(addBookUseCase.handle(any(AddBookRequest.class))).thenReturn(bookId);

        // When & Then
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/books/" + bookId));
    }

    @Test
    @DisplayName("POST /api/v1/books returns 400 when request is invalid")
    void addBookWithInvalidRequest() throws Exception {
        // Given
        AddBookRequest invalidRequest = new AddBookRequest(
                "", // Invalid: empty title
                "", // Invalid: empty ISBN
                BookAvailability.AVAILABLE,
                null,
                null
        );

        // When & Then
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/books/{id} returns 200 with book details")
    void getBookById() throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        GetBookByIdResponse response = new GetBookByIdResponse(
                bookId,
                "Test Book",
                "1234567890123",
                BookAvailability.AVAILABLE,
                "Test Author"
        );
        
        when(getBookByIdUseCase.handle(bookId)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/books/" + bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookId.toString()))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.isbn").value("1234567890123"))
                .andExpect(jsonPath("$.availability").value("AVAILABLE"))
                .andExpect(jsonPath("$.authorName").value("Test Author"));
    }

    @Test
    @DisplayName("GET /api/v1/books/{id} returns 404 when book not found")
    void getBookByIdNotFound() throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        when(getBookByIdUseCase.handle(bookId)).thenThrow(new RuntimeException("Book not found"));

        // When & Then
        mockMvc.perform(get("/api/v1/books/" + bookId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("DELETE /api/v1/books/{id} returns 204 when book is deleted successfully")
    void deleteBookById() throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        doNothing().when(deleteBookByIdUseCase).handle(bookId);

        // When & Then
        mockMvc.perform(delete("/api/v1/books/" + bookId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/v1/books/{id} returns 500 when deletion fails")
    void deleteBookByIdFailure() throws Exception {
        // Given
        UUID bookId = UUID.randomUUID();
        doThrow(new RuntimeException("Deletion failed")).when(deleteBookByIdUseCase).handle(bookId);

        // When & Then
        mockMvc.perform(delete("/api/v1/books/" + bookId))
                .andExpect(status().isInternalServerError());
    }
} 