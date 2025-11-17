package com.hardnets.coop.service;

import com.hardnets.coop.model.entity.UserEntity;
import com.hardnets.coop.repository.UserRepository;
import com.hardnets.coop.service.impl.UserDetailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserDetailServiceImpl userDetailService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void signup() {
    }

    @Test
    void updateProfile() {
    }

    @Test
    void updatePassword() {
        String dni = "11111111-1";
        String password = "the new password";

        when(userRepository.findById(dni)).thenReturn(Optional.of(new UserEntity()));

        userDetailService.updatePassword(dni, password);
    }
}
