package co.com.juanjogv.lms.infrastructure.rest.config;

import co.com.juanjogv.lms.application.dto.GenericExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericExceptionResponse> handleNoSuchElementException(IllegalArgumentException ex) {

        final var badRequestError = HttpStatus.BAD_REQUEST;
        final var exceptionMsg = "Invalid request. The request contains invalid or incomplete data.";

        final var genericExceptionResponse = new GenericExceptionResponse(
                OffsetDateTime.now(),
                badRequestError.value(),
                badRequestError.getReasonPhrase(),
                Optional.ofNullable(ex.getMessage()).orElse(exceptionMsg)
        );

        return ResponseEntity.status(badRequestError).body(genericExceptionResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<GenericExceptionResponse> handleNoSuchElementException(NoSuchElementException ex) {

        final var notFoundError = HttpStatus.NOT_FOUND;
        final var exceptionMsg = "The resource you are looking for is not found.";

        final var genericExceptionResponse = new GenericExceptionResponse(
                OffsetDateTime.now(),
                notFoundError.value(),
                notFoundError.getReasonPhrase(),
                Optional.ofNullable(ex.getMessage()).orElse(exceptionMsg)
        );


        return ResponseEntity.status(notFoundError).body(genericExceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericExceptionResponse> handleAnyUnhandledException(Exception ex) {
        final var exceptionMessage = MessageFormat.format(
                "Technical error: IDENTIFIER {0} - Internal system error please contact your system administrator.",
                UUID.randomUUID()
        );
        log.error(exceptionMessage, ex);

        final var internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        final var genericExceptionResponse = new GenericExceptionResponse(
                OffsetDateTime.now(),
                internalServerError.value(),
                internalServerError.getReasonPhrase(),
                exceptionMessage
        );

        return ResponseEntity.status(internalServerError).body(genericExceptionResponse);
    }
}
