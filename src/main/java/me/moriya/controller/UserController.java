package me.moriya.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserController {

    @PostMapping
    ResponseEntity<Mono<Void>> save(@RequestBody UserRequest userRequest);

    @GetMapping("/{id}")
    ResponseEntity<Mono<UserResponse>> find(@PathVariable String id);

    @GetMapping
    ResponseEntity<Flux<UserResponse>> findAll();

    @PatchMapping(value = "/{id}")
    ResponseEntity<Mono<UserResponse>> update(@PathVariable String id,
                                              @RequestBody UserRequest userRequest);

    @DeleteMapping("/{id}")
    ResponseEntity<Mono<Void>> delete(@PathVariable String id);

}
