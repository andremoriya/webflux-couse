package me.moriya.controller.impl;

import lombok.RequiredArgsConstructor;
import me.moriya.controller.UserController;
import me.moriya.model.request.UserRequest;
import me.moriya.model.response.UserResponse;
import me.moriya.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<Mono<Void>> save(UserRequest userRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(userRequest));
    }

    @Override
    public ResponseEntity<Mono<UserResponse>> findById(String id) {
        return ResponseEntity
                .ok(userService.findById(id));
    }

    @Override
    public ResponseEntity<Flux<UserResponse>> findAll() {
        return ResponseEntity
                .ok(userService.findAll());
    }

    @Override
    public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest userRequest) {
        return ResponseEntity.ok(
                userService.update(id, userRequest)
        );
    }

    @Override
    public ResponseEntity<Mono<Void>> delete(String id) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(userService.delete(id));
    }

}
