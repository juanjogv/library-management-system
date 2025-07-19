package co.com.juanjogv.lms.application.dto.book;

import co.com.juanjogv.lms.domain.model.BookAvailability;

import java.util.UUID;

public record GetAllBooksResponse(
        UUID id,
        String title,
        String isbn,
        BookAvailability availability
) {}
