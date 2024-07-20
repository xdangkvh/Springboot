package com.example.demo.exception;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

// import org.springframework.web.context.request.WebRequest;
import jakarta.validation.ConstraintViolation;
// import javax.validation.ConstraintViolation;
// import org.springframework.security.access.AccessDeniedException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),
                HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex,
    // WebRequest request) {
    // ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),
    // HttpStatus.INTERNAL_SERVER_ERROR.value());
    // return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ErrorResponse> handlingRuntimeException(RuntimeException exception) {
        log.error("Exception: ", exception);
        ErrorResponse apiResponse = new ErrorResponse();

        apiResponse.setStatus(Error.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(Error.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ErrorResponse> handlingAppException(AppException exception) {
        Error error = exception.getError();
        ErrorResponse apiResponse = new ErrorResponse();

        apiResponse.setStatus(error.getCode());
        apiResponse.setMessage(error.getMessage());

        return ResponseEntity.status(error.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ErrorResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        Error error = Error.UNAUTHORIZED;

        return ResponseEntity.status(error.getStatusCode())
                .body(ErrorResponse.builder()
                        .status(error.getCode())
                        .message(error.getMessage())
                        .build());
    }

    @SuppressWarnings("unchecked")
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        Error errorCode = Error.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = Error.valueOf(enumKey);

            var constraintViolation = exception.getBindingResult().getAllErrors().get(0)
                    .unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (IllegalArgumentException e) {

        }

        ErrorResponse apiResponse = new ErrorResponse();

        apiResponse.setStatus(errorCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
