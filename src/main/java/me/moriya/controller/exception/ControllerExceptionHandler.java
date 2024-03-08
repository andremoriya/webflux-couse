package me.moriya.controller.exception;

import me.moriya.service.exception.NotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
                                        .status(BAD_REQUEST.value())
                                        .error(BAD_REQUEST.getReasonPhrase())
                                        .message(verifyDuplicateKey(e.getMessage()))
                                        .path(request.getPath().toString())
                                        .build()
                        )
                );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Mono<ValidationError>> validationError(
            WebExchangeBindException e, ServerHttpRequest request) {
        var error = new ValidationError(
                request.getId(),
                LocalDateTime.now(),
                request.getPath().toString(),
                BAD_REQUEST.value(),
                "Validation Error",
                "Error on validation attributes"
        );

        error.addErrors(e.getBindingResult().getFieldErrors());

        return ResponseEntity.badRequest()
                .body(Mono.just(error));
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Mono<StandardError>> notFoundException(NotFoundException e, ServerHttpRequest request) {
        return ResponseEntity.status(NOT_FOUND)
                .body(
                        Mono.just(
                                StandardError
                                        .builder()
                                        .requestId(request.getId())
                                        .timestamp(LocalDateTime.now())
                                        .status(NOT_FOUND.value())
                                        .error(NOT_FOUND.getReasonPhrase())
                                        .message(e.getMessage())
                                        .path(request.getPath().toString())
                                        .build()
                        )
                );
    }


    private String verifyDuplicateKey(String message) {
        if (message.contains("email dup key")) {
            return "Email already exists";
        }
        return message;
    }

}
