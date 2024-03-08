package me.moriya.controller.exceptions;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class ValidationError extends StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<ValidationFieldError> errors = new ArrayList<>(0);

    public ValidationError(String requestId, LocalDateTime timestamp, String path,
                           Integer status, String error, String message) {
        super(requestId, timestamp, path, status, error, message);
    }

    public void addErrors(String field, String message) {
        errors.add(new ValidationFieldError(field, message));
    }

    public void addErrors(Collection<FieldError> errors) {
        errors.forEach(error -> this.addErrors(error.getField(), error.getDefaultMessage()));
    }



    private record ValidationFieldError(String field, String message) implements Serializable {
            @Serial
            private static final long serialVersionUID = 1L;

    }
}
