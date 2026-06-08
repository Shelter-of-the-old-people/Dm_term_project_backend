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
        MockHttpSession developerSession = loginDeveloper();
        Long projectId = createProject(loginClient(), "outsourcing", "outsourcing-test-project");

        MvcResult applyResult = mockMvc.perform(post("/api/projects/{projectId}/applications", projectId)
                        .session(developerSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employmentType": "outsourcing",
                                  "workDays": 30,
                                  "bidAmount": 4500,
                                  "content": "I can deliver this outsourcing project within the proposed schedule."
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(projectId.intValue()))
                .andExpect(jsonPath("$.data.applicationCount").value(1))
                .andReturn();

        Object applicationId = JsonPathUtil.read(applyResult.getResponse().getContentAsString(), "$.data.applicationId");

        mockMvc.perform(get("/api/developer/applications").session(developerSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].projectId").value(projectId.intValue()))
                .andExpect(jsonPath("$.data[0].employmentType").value("outsourcing"))
                .andExpect(jsonPath("$.data[0].estimateAmount").value(4500))
                .andExpect(jsonPath("$.data[0].applicationCount").value(1))
                .andExpect(jsonPath("$.data[0].workDays").value(30));

        mockMvc.perform(get("/api/developer/applications/{applicationId}", applicationId).session(developerSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(projectId.intValue()))
                .andExpect(jsonPath("$.data.bidAmount").value(4500))
                .andExpect(jsonPath("$.data.headcount").value(1))
                .andExpect(jsonPath("$.data.onsiteLines").isEmpty());
    }

    @Test
    void residentApplicationAcceptsOnsiteLines() throws Exception {
        MockHttpSession developerSession = loginDeveloper();
        Long projectId = createProject(loginClient(), "resident", "resident-test-project");

        mockMvc.perform(post("/api/projects/{projectId}/applications", projectId)
                        .session(developerSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employmentType": "resident",
                                  "content": "I can join this resident project with a structured plan.",
                                  "onsiteLines": [
                                    {
                                      "skillCategory": "Frontend",
                                      "careerLevel": "mid",
                                      "headCount": 2,
                                      "monthlyPay": 500,
                                      "sortOrder": 1
                                    }
                                  ]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(projectId.intValue()))
                .andExpect(jsonPath("$.data.applicationCount").value(1));

        mockMvc.perform(get("/api/developer/applications").session(developerSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].projectId").value(projectId.intValue()))
                .andExpect(jsonPath("$.data[0].employmentType").value("resident"))
                .andExpect(jsonPath("$.data[0].estimateAmount").value(500));
    }

    @Test
    void applicationRejectsContactInfo() throws Exception {
        MockHttpSession developerSession = loginDeveloper();
        Long projectId = createProject(loginClient(), "outsourcing", "contact-info-test-project");

        mockMvc.perform(post("/api/projects/{projectId}/applications", projectId)
                        .session(developerSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employmentType": "outsourcing",
                                  "workDays": 21,
                                  "bidAmount": 3800,
                                  "content": "Please contact me at test@example.com for faster discussion."
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void applicationRejectsDuplicateSubmission() throws Exception {
        MockHttpSession developerSession = loginDeveloper();
        Long projectId = createProject(loginClient(), "outsourcing", "duplicate-test-project");

        mockMvc.perform(post("/api/projects/{projectId}/applications", projectId)
                        .session(developerSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employmentType": "outsourcing",
                                  "workDays": 14,
                                  "bidAmount": 3200,
                                  "content": "I have prior experience improving similar project flows."
                                }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/projects/{projectId}/applications", projectId)
                        .session(developerSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employmentType": "outsourcing",
                                  "workDays": 14,
                                  "bidAmount": 3200,
                                  "content": "Submitting a second application should be blocked."
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void developerApplicationEndpointsRequireDeveloperRole() throws Exception {
        MockHttpSession session = loginClient();

        mockMvc.perform(get("/api/developer/applications").session(session))
                .andExpect(status().isForbidden());
    }

    private MockHttpSession loginDeveloper() throws Exception {
        return login("developer1", "1234");
    }

    private MockHttpSession loginClient() throws Exception {
        return login("client1", "1234");
    }

    private MockHttpSession login(String loginId, String password) throws Exception {
        MvcResult loginResult = mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "loginId": "%s",
                                  "password": "%s"
                                }
                                """.formatted(loginId, password)))
                .andExpect(status().isOk())
                .andReturn();

        return (MockHttpSession) loginResult.getRequest().getSession(false);
    }

    private Long createProject(MockHttpSession clientSession, String projectType, String title) throws Exception {
        MvcResult createResult = mockMvc.perform(post("/api/client/projects")
                        .session(clientSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "%s",
                                  "recruitmentDeadline": "2099-12-31",
                                  "projectType": "%s",
                                  "budgetAmount": 4800,
                                  "expectedDurationDays": 45,
                                  "projectFields": ["dev"],
                                  "planningStatus": "planned",
                                  "meetingRegion": "seoul",
                                  "workDescription": "Test project for application flow verification.",
                                  "progressMethod": "online meeting",
                                  "techStacks": ["React", "Spring Boot"],
                                  "kickoffSchedule": "within one week"
                                }
                                """.formatted(title, projectType)))
                .andExpect(status().isOk())
                .andReturn();

        return Long.valueOf(JsonPathUtil.read(
                createResult.getResponse().getContentAsString(),
                "$.data.projectId").toString());
    }
}
