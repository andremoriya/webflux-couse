package me.moriya.service;

import lombok.RequiredArgsConstructor;
import me.moriya.mapper.UserMapper;
import me.moriya.model.request.UserRequest;
import me.moriya.model.response.UserResponse;
import me.moriya.repository.UserRepository;
import me.moriya.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public Mono<Void> save(final UserRequest userRequest) {
        return userRepository
                .save(
                        userMapper.toEntity(userRequest)
                ).then(); // Added .then() to return a Mono<Void>, because the method save() returns a Mono<User>
    }

    public Mono<UserResponse> findById(final String id) {
        return userRepository.findById(id)
                .map(userMapper::toReponse)
                .switchIfEmpty(
                        Mono.error(new NotFoundException("User with ID %s not found.".formatted(id)))
                );
    }

    public Flux<UserResponse> findAll() {
        return userRepository.findAll()
                .map(userMapper::toReponse);
    }

    public Mono<UserResponse> update(final String id, final UserRequest userRequest) {
        return findById(id)
                .map(userMapper::toEntity)
                .map(user -> userMapper.toEntity(userRequest, user))
                .flatMap(userRepository::save)
                .map(userMapper::toReponse);
    }

}
