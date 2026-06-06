package com.github.term_project.domain.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:auth-api-test;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginStoresSessionAndSessionEndpointReturnsUser() throws Exception {
        MvcResult loginResult = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "loginId": "developer1",
                                  "password": "1234",
                                  "role": "developer"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loginId").value("developer1"))
                .andExpect(jsonPath("$.data.nickname").value("Developer Demo"))
                .andExpect(jsonPath("$.data.role").value("developer"))
                .andReturn();

        HttpSession session = loginResult.getRequest().getSession(false);

        mockMvc.perform(get("/api/session").session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loginId").value("developer1"))
                .andExpect(jsonPath("$.data.role").value("developer"));
    }

    @Test
    void signupCreatesUserAndLogsIn() throws Exception {
        MvcResult signupResult = mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "loginId": "tester@example.com",
                                  "nickname": "Tester",
                                  "password": "1234",
                                  "role": "client"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loginId").value("tester@example.com"))
                .andExpect(jsonPath("$.data.nickname").value("Tester"))
                .andExpect(jsonPath("$.data.role").value("client"))
                .andReturn();

        HttpSession session = signupResult.getRequest().getSession(false);

        mockMvc.perform(get("/api/session").session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loginId").value("tester@example.com"))
                .andExpect(jsonPath("$.data.role").value("client"));
    }

    @Test
    void sessionEndpointRequiresLogin() throws Exception {
        mockMvc.perform(get("/api/session"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void logoutClearsSession() throws Exception {
        MvcResult loginResult = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "loginId": "client1",
                                  "password": "1234",
                                  "role": "client"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);

        mockMvc.perform(post("/api/logout").session(session))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/session").session(session))
                .andExpect(status().isUnauthorized());
    }
}
