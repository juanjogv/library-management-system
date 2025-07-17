package co.com.juanjogv.lms.application.dto.book;

import co.com.juanjogv.lms.domain.model.BookAvailability;

public record GetAllBooksResponse(
        String title,
        String isbn,
        BookAvailability availability
) {}
