package com.github.term_project.domain.application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:application-api-test;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
@Transactional
class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void outsourcingApplicationCreatesHistoryAndIncrementsApplicantCount() throws Exception {
        MockHttpSession session = loginDeveloper();

        MvcResult applyResult = mockMvc.perform(post("/api/projects/1/applications")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employmentType": "outsourcing",
                                  "workDays": 30,
                                  "bidAmount": 4500,
                                  "content": "프로젝트 경험과 일정 대응 역량을 바탕으로 성실하게 진행하겠습니다."
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(1))
                .andExpect(jsonPath("$.data.applicationCount").value(1))
                .andReturn();

        Object applicationId = JsonPathUtil.read(applyResult.getResponse().getContentAsString(), "$.data.applicationId");

        mockMvc.perform(get("/api/developer/applications").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].projectId").value(1))
                .andExpect(jsonPath("$.data[0].employmentType").value("outsourcing"))
                .andExpect(jsonPath("$.data[0].estimateAmount").value(4500))
                .andExpect(jsonPath("$.data[0].applicationCount").value(1))
                .andExpect(jsonPath("$.data[0].workDays").value(30));

        mockMvc.perform(get("/api/developer/applications/{applicationId}", applicationId).session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(1))
                .andExpect(jsonPath("$.data.bidAmount").value(4500))
                .andExpect(jsonPath("$.data.headcount").value(1))
                .andExpect(jsonPath("$.data.onsiteLines").isEmpty());
    }

    @Test
    void residentApplicationAcceptsOnsiteLines() throws Exception {
        MockHttpSession session = loginDeveloper();

        mockMvc.perform(post("/api/projects/3/applications")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "applicationType": "onsite",
                                  "content": "상주 협업 경험을 살려 안정적으로 운영하겠습니다.",
                                  "onsiteLines": [
                                    {
                                      "skillCategory": "프론트엔드",
                                      "careerLevel": "중급",
                                      "headCount": 2,
                                      "monthlyPay": 500,
                                      "sortOrder": 1
                                    }
                                  ]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(3))
                .andExpect(jsonPath("$.data.applicationCount").value(1));

        mockMvc.perform(get("/api/developer/applications").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].projectId").value(3))
                .andExpect(jsonPath("$.data[0].employmentType").value("resident"))
                .andExpect(jsonPath("$.data[0].estimateAmount").value(500));
    }

    @Test
    void applicationRejectsContactInfo() throws Exception {
        MockHttpSession session = loginDeveloper();

        mockMvc.perform(post("/api/projects/6/applications")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employmentType": "outsourcing",
                                  "workDays": 21,
                                  "bidAmount": 3800,
                                  "content": "문의는 test@example.com 으로 부탁드립니다."
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("지원 내용에 이메일 또는 전화번호를 포함할 수 없습니다."));
    }

    @Test
    void applicationRejectsDuplicateSubmission() throws Exception {
        MockHttpSession session = loginDeveloper();

        mockMvc.perform(post("/api/projects/8/applications")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employmentType": "outsourcing",
                                  "workDays": 14,
                                  "bidAmount": 3200,
                                  "content": "검색 기능 개선 경험이 있습니다."
                                }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/projects/8/applications")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employmentType": "outsourcing",
                                  "workDays": 14,
                                  "bidAmount": 3200,
                                  "content": "한 번 더 지원해봅니다."
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 지원한 프로젝트입니다."));
    }

    @Test
    void developerApplicationEndpointsRequireDeveloperRole() throws Exception {
        MockHttpSession session = loginClient();

        mockMvc.perform(get("/api/developer/applications").session(session))
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
