package me.moriya.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<FieldError> errors = new ArrayList<>(0);

    public ValidationError(String requestId, LocalDateTime timestamp, String path,
                           Integer status, String error, String message) {
        super(requestId, timestamp, path, status, error, message);
    }

    public void addError(String field, String message) {
        errors.add(new FieldError(field, message));
    }

    @Getter
    @AllArgsConstructor
    private static final class FieldError implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private final String field;
        private final String message;

    }
}
