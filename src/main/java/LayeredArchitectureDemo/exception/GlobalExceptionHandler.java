package LayeredArchitectureDemo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

/**
 * Global exception handler for REST APIs
 * Intercepts exceptions thrown in REST API
 * Converts them into the unified format
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final HttpServletRequest request;

    public GlobalExceptionHandler(HttpServletRequest request) {
        this.request = request;
    }


    /**
     * Catches exceptions in processing messages
     * Returns customized exceptions
     */
    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorResponse> messageException(MessageException e) {
        return buildErrorResponse(
                ErrorMessage.builder()
                        .status(e.getErrorMessage().getStatus())
                        .error(e.getErrorMessage().getError())
                        .uri(request.getRequestURI())
                        .timestamp(Instant.now())
                        .build()
        );
    }

    /**
     * Catches unknown exceptions not handled by the previous handlers
     * Returns customized exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        LOG.error("Unknown exception:" + e);
        return buildErrorResponse(
                ErrorMessage.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .error(e.getMessage())
                        .timestamp(Instant.now())
                        .build()
        );
    }

    /**
     * Casts error message to response
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorMessage errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse(
                errorMessage.getStatus().value(),
                errorMessage.getError(),
                request.getRequestURI(),
                Instant.now()
        );
        return ResponseEntity.status(errorMessage.getStatus()).body(errorResponse);
    }
}
