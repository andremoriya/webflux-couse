package me.moriya.service;

import lombok.RequiredArgsConstructor;
import me.moriya.entity.User;
import me.moriya.mapper.UserMapper;
import me.moriya.model.request.UserRequest;
import me.moriya.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public Mono<User> save(final UserRequest userRequest) {
        return userRepository.save(userMapper.toEntity(userRequest));
    }

    public Mono<User> findById(final String id) {
        return userRepository.findById(id);
    }

}
