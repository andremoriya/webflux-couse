package me.moriya.service;

import me.moriya.entity.User;
import me.moriya.mapper.UserMapper;
import me.moriya.model.request.UserRequest;
import me.moriya.model.response.UserResponse;
import me.moriya.repository.UserRepository;
import me.moriya.service.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testSave() {
        var request = new UserRequest("Andre Moriya", "andremoriya@gmail.com", "123456");
        var user = User.builder().build();

        when(userMapper.toEntity(any(UserRequest.class)))
                .thenReturn(user);

        when(userRepository.save(any(User.class)))
                .thenReturn(Mono.just(User.builder().build()));

        Mono<Void> result = userService.save(request);

        /*
         * Verifica o comportamento de um Publisher
         * StepVerifier é uma ferramenta do Reactor que permite verificar o comportamento de um Publisher
         * 1 - Criar um Step com base no Publisher (result)
         */
        StepVerifier.create(result) // 1
                .expectNextCount(0)
                .expectComplete()
                .verify();

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testFindById() {
        when(userRepository.findById("1"))
                .thenReturn(Mono.just(User.builder().build()));

        when(userMapper.toReponse(any(User.class)))
                .thenReturn(new UserResponse("1", "Andre Moriya", "", ""));

        Mono<UserResponse> result = userService.findById("1");

        StepVerifier.create(result)
                .expectNextMatches(userResponse -> userResponse.id().equals("1"))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll())
                .thenReturn(Flux.just(User.builder().build()));

        when(userMapper.toReponse(any(User.class)))
                .thenReturn(new UserResponse("1", "Andre Moriya", "", ""));

        Flux<UserResponse> result = userService.findAll();

        StepVerifier.create(result)
                .expectNextMatches(userResponse -> userResponse.id().equals("1"))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void testUpdate() {
        var request = new UserRequest("Andre Moriya", "andremoriya@gmail.com", "123456");
        var user = User.builder().build();
        var userResponse = new UserResponse("1", "Andre Moriya", "", "");

        when(userRepository.findById("1"))
                .thenReturn(Mono.just(user));

        when(userMapper.toEntity(any(UserResponse.class)))
                .thenReturn(user);

        when(userMapper.toEntity(any(UserRequest.class), any(User.class)))
                .thenReturn(user);

        when(userRepository.save(any(User.class)))
                .thenReturn(Mono.just(User.builder().build()));

        when(userMapper.toReponse(any(User.class)))
                .thenReturn(userResponse);

        Mono<UserResponse> result = userService.update("1", request);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.id().equals("1"))
                .expectNextCount(0)
                .expectComplete()
                .verify();

        verify(userRepository).findById("1");
    }

    @Test
    void testDelete() {
        when(userRepository.findAndRemove(anyString()))
                .thenReturn(Mono.just(User.builder().build()));

        Mono<Void> result = userService.delete("1");

        StepVerifier.create(result)
                .expectNextCount(0)
                .expectComplete()
                .verify();

        verify(userRepository).findAndRemove("1");
    }

    @Test
    void testDeleteHandleNotFoundException() {
        when(userRepository.findAndRemove(anyString()))
                .thenReturn(Mono.empty());

        Mono<Void> result = userService.delete("1");

        Predicate<Throwable> testNotFoundException = throwable ->
                throwable instanceof NotFoundException &&
                        throwable.getMessage().equals("User with ID 1 not found.");

        StepVerifier.create(result)
                .expectErrorMatches(testNotFoundException)
                .verify();

        verify(userRepository).findAndRemove("1");
    }
}