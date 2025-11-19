package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.payload.ErrorPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class CommonErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonErrorHandler.class);
    private static final String UNEXPECTED_ERROR_OCCURRED = "Unexpected error occurred";

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorPayload> handleCommonException(Exception ex) {

        LOGGER.error("[CommonError]: ", ex);

        final Integer statusCode = ex instanceof HttpStatusCodeException
                ? ((HttpStatusCodeException) ex).getStatusCode().value()
                : ex instanceof ResponseStatusException
                ? ((ResponseStatusException) ex).getStatusCode().value()
                : HttpStatus.INTERNAL_SERVER_ERROR.value();

        final String message = ex instanceof HttpStatusCodeException
                ? ex.getMessage()
                : ex instanceof ResponseStatusException
                ? ((ResponseStatusException) ex).getReason()
                : UNEXPECTED_ERROR_OCCURRED;


        return ResponseEntity
            .internalServerError()
            .body(
                ErrorPayload.builder()
                    .status(statusCode)
                    .reason(message)
                    .build());
    }

}
