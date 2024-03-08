package me.moriya.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @Size(min = 3, max = 50, message = "Must be between 3 and 50 characters")
        @NotBlank(message = "must not be empty or null")
        String name,

        @Email(message = "Invalid email address")
        @NotBlank(message = "Must not be empty or null")
        String email,

        @NotBlank(message = "Must not be empty or null")
        @Size(min = 6, max = 20, message = "Must be between 6 and 20 characters")
        String password
) { }
