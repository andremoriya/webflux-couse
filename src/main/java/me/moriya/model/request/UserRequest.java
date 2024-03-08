package me.moriya.model.request;

public record UserRequest(
        String name,
        String email,
        String password
) { }
