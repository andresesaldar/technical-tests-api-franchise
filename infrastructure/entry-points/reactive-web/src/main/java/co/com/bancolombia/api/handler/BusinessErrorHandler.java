package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.payload.BusinessErrorPayload;
import co.com.bancolombia.error.InvalidParamException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessErrorHandler {
    @ExceptionHandler(InvalidParamException.class)
    ResponseEntity<BusinessErrorPayload> handleInvalidParamException(InvalidParamException ex) {
        return ResponseEntity
            .badRequest()
            .body(
                BusinessErrorPayload.builder()
                    .status(ex.getStatus())
                    .reason(HttpStatus.valueOf(ex.getStatus()).getReasonPhrase())
                    .code(ex.getCustomError().getCode())
                    .message(ex.getCustomError().getMessage())
                    .build());
    }

}
