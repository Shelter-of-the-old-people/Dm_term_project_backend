package com.github.term_project.domain.profile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.term_project.domain.application.JsonPathUtil;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:developer-profile-api-test;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "app.upload.root-dir=build/test-uploads/profile-api-test"
})
@AutoConfigureMockMvc
@Transactional
class DeveloperProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void developerCanReadAndUpdateProfile() throws Exception {
        MockHttpSession developerSession = loginDeveloper();

        mockMvc.perform(get("/api/developer/profile").session(developerSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loginId").value("developer1"))
                .andExpect(jsonPath("$.data.supportFields[0]").value("개발"));

        mockMvc.perform(put("/api/developer/profile")
                        .session(developerSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "supportFields": ["개발", "디자인", "개발"],
                                  "activeAvailable": true,
                                  "onsiteAvailable": false,
                                  "regionSido": "경기",
                                  "regionSigungu": "성남시",
                                  "businessType": "법인사업자",
                                  "careerYears": 9,
                                  "searchTags": ["Java", "Spring", "Java", "MSA"],
                                  "introduction": "백엔드 중심으로 프로젝트를 수행해 온 개발자입니다."
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.supportFields.length()").value(2))
                .andExpect(jsonPath("$.data.searchTags.length()").value(3))
                .andExpect(jsonPath("$.data.regionSido").value("경기"))
                .andExpect(jsonPath("$.data.businessType").value("법인사업자"))
                .andExpect(jsonPath("$.data.careerYears").value(9));
    }

    @Test
    void developerCanUploadProfileImageAndStoredUrlIsReturned() throws Exception {
        MockHttpSession developerSession = loginDeveloper();

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "profile.png",
                MediaType.IMAGE_PNG_VALUE,
                "png-binary".getBytes());

        MvcResult uploadResult = mockMvc.perform(multipart("/api/developer/profile/image")
                        .file(file)
                        .session(developerSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.imageUrl").value(org.hamcrest.Matchers.startsWith("/files/profile/")))
                .andReturn();

        String imageUrl = JsonPathUtil.read(uploadResult.getResponse().getContentAsString(), "$.data.imageUrl").toString();
        String filename = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);

        Path storedFile = Path.of("build", "test-uploads", "profile-api-test", "profile", filename);
        org.junit.jupiter.api.Assertions.assertTrue(Files.exists(storedFile));

        mockMvc.perform(get("/api/developer/profile").session(developerSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.profileImageUrl").value(imageUrl));
    }

    @Test
    void profileRejectsTooManySearchTags() throws Exception {
        MockHttpSession developerSession = loginDeveloper();

        mockMvc.perform(put("/api/developer/profile")
                        .session(developerSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "supportFields": ["개발"],
                                  "activeAvailable": true,
                                  "onsiteAvailable": true,
                                  "regionSido": "서울",
                                  "regionSigungu": "강남구",
                                  "businessType": "개인사업자",
                                  "careerYears": 4,
                                  "searchTags": ["a", "b", "c", "d", "e", "f"],
                                  "introduction": "태그 제한을 검증합니다."
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void clientCannotAccessDeveloperProfileEndpoints() throws Exception {
        MockHttpSession clientSession = loginClient();

        mockMvc.perform(get("/api/developer/profile").session(clientSession))
                .andExpect(status().isForbidden());
    }

    private MockHttpSession loginDeveloper() throws Exception {
        return login("developer1", "1234", "developer");
    }

    private MockHttpSession loginClient() throws Exception {
        return login("client1", "1234", "client");
    }

    private MockHttpSession login(String loginId, String password, String role) throws Exception {
        MvcResult loginResult = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "loginId": "%s",
                                  "password": "%s",
                                  "role": "%s"
                                }
                                """.formatted(loginId, password, role)))
                .andExpect(status().isOk())
                .andReturn();

        return (MockHttpSession) loginResult.getRequest().getSession(false);
    }
}
