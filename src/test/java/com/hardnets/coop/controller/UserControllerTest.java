package com.hardnets.coop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardnets.coop.model.dto.UserDto;
import com.hardnets.coop.service.impl.UserDetailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockitoBean
    UserDetailServiceImpl userDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void updatePassword() throws Exception {
        String dni = "11111111-1";
        var dto = new UserDto();
        dto.setDni(dni);
        when(userDetailService.getByDni(dni)).thenReturn(dto);

        when(userDetailService.update(any(UserDto.class))).thenReturn(mock(UserDto.class));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/v1/user/" + dni)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                                .with(user("user"))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn();
    }
}
