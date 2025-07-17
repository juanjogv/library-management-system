package co.com.juanjogv.lms.application.dto;

import java.time.OffsetDateTime;

public record GenericExceptionResponse(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message
) {}
