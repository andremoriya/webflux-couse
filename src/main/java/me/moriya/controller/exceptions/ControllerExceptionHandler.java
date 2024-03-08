package me.moriya.controller.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<Mono<StandardError>> duplicatedKeyException(DuplicateKeyException e, ServerHttpRequest request) {
        return ResponseEntity.badRequest()
                .body(
                        Mono.just(
                                StandardError
                                        .builder()
                                        .requestId(request.getId())
                                        .timestamp(LocalDateTime.now())
                                        .status(HttpStatus.BAD_REQUEST.value())
                                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                        .message(verifyDuplicateKey(e.getMessage()))
                                        .path(request.getPath().toString())
                        .build())
                );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Mono<ValidationError>> validationError(
            WebExchangeBindException e, ServerHttpRequest request) {
        var error = new ValidationError(
                request.getId(),
                LocalDateTime.now(),
                request.getPath().toString(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Error on validation attributes"
        );

        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> error.addError(fieldError.getField(), fieldError.getDefaultMessage())
        );

        return ResponseEntity.badRequest()
                .body(Mono.just(error));
    }

    private String verifyDuplicateKey(String message) {
        if (message.contains("email dup key")) {
            return "Email already exists";
        }
        return message;
    }

}
