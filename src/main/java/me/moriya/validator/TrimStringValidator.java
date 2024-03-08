package me.moriya.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TrimStringValidator implements ConstraintValidator<TrimString, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        String message = extractMessage(value);
        if(null != message) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }
        return true;
    }

    private String extractMessage(String value) {
        var startWithWhiteSpaces = value.matches("^\\s.*");
        var endWithWhiteSpaces = value.matches(".*\\s$");

        if (startWithWhiteSpaces && endWithWhiteSpaces) {
            return "Contains white spaces in the beginning and end";
        } else if (startWithWhiteSpaces) {
            return "Contains white spaces in the beginning";
        } else if (endWithWhiteSpaces) {
            return "Contains white spaces in the end";
        }
        return null;
    }
}
