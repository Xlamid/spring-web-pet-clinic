package git.xlamid.springwebpetclinic.handler;

import git.xlamid.springwebpetclinic.dto.ErrorMessageResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponseDto> exceptionNotValidHandler(MethodArgumentNotValidException e) {
        log.error("Validation error:", e);
        String detailedMessage = e.getBindingResult()
                .getFieldErrors().stream()
                .map(error ->
                        String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessageResponseDto(
                        "Validation error",
                        detailedMessage,
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorMessageResponseDto> exceptionIllegalStateHandler(IllegalStateException e) {
        log.error("Conflict states error:", e);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessageResponseDto(
                        "Illegal state error",
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessageResponseDto> exceptionNotFoundHandler(NoSuchElementException e) {
        log.error("Not found error:", e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessageResponseDto(
                        "Not found error",
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponseDto> exceptionHandler(Exception e) {
        log.error("Internal server error:", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessageResponseDto(
                        "Internal server error",
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }
}