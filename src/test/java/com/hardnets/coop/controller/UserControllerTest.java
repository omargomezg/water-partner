package com.hardnets.coop.controller;

import com.hardnets.coop.service.impl.UserDetailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserDetailServiceImpl userDetailService;

    @Test
    void updatePassword() throws Exception {
        String dni = "11111111-1";
        String password = "the new password";

        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/user/" + dni + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(password)
                        .with(user("user")))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
