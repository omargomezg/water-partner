package com.hardnets.coop.service;

import com.hardnets.coop.exception.UserException;
import com.hardnets.coop.model.dto.CreateUserDto;
import com.hardnets.coop.model.dto.UserDto;
import com.hardnets.coop.model.entity.UserEntity;
import com.hardnets.coop.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        this.userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void create_success() throws Exception {
        UserEntity user = mock(UserEntity.class);
        CreateUserDto newUser = mock(CreateUserDto.class);
        when(userRepository.findByRutOrEmail(user.getRut(), user.getEmail())).thenReturn(new ArrayList<>());
        when(userRepository.save(any(UserEntity.class))).thenReturn(mock(UserEntity.class));
        UserDto response = userService.create(newUser);
        assertNotNull(response);
    }

    @Test(expected = UserException.class)
    public void create_user_exists() throws Exception {
        UserEntity user = mock(UserEntity.class);
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        CreateUserDto newUser = mock(CreateUserDto.class);
        when(userRepository.findByRutOrEmail(user.getRut(), user.getEmail())).thenReturn(users);
        userService.create(newUser);
    }

    @Test
    public void loadUserByUsername_success() {
        String email = "omar.fdo.gomez@gmail.com";
        UserEntity user = new UserEntity();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserDetails userDetails = userService.loadUserByUsername(email);
        assertNotNull(userDetails);
    }
}
