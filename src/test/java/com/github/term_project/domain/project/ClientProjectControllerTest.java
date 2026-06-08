package com.github.term_project.domain.project;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.term_project.domain.application.JsonPathUtil;
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
        "spring.datasource.url=jdbc:h2:mem:client-project-api-test;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
@Transactional
class ClientProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void clientCanCreateProjectAndSeeItInOwnList() throws Exception {
        MockHttpSession clientSession = loginClient();

        mockMvc.perform(post("/api/client/projects")
                        .session(clientSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "new reservation service backoffice",
                                  "recruitmentDeadline": "2099-12-31",
                                  "projectType": "outsourcing",
                                  "budgetAmount": 4800,
                                  "expectedDurationDays": 45,
                                  "projectFields": ["dev", "planning"],
                                  "planningStatus": "requirements organized",
                                  "meetingRegion": "seoul gangnam",
                                  "workDescription": "Build a reservation dashboard and operations backoffice.",
                                  "progressMethod": "weekly meeting and async collaboration",
                                  "techStacks": ["React", "Spring Boot"],
                                  "kickoffSchedule": "within one week"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").isNumber());

        mockMvc.perform(get("/api/client/projects").session(clientSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("new reservation service backoffice"))
                .andExpect(jsonPath("$.data[0].employmentType").value("outsourcing"))
                .andExpect(jsonPath("$.data[0].applicationCount").value(0));
    }

    @Test
    void clientCanReadApplicantsAndApplicationDetailForOwnedProject() throws Exception {
        MockHttpSession clientSession = loginClient();

        MvcResult createResult = mockMvc.perform(post("/api/client/projects")
                        .session(clientSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "operations admin feature project",
                                  "recruitmentDeadline": "2099-11-30",
                                  "projectType": "outsourcing",
                                  "budgetAmount": 3500,
                                  "expectedDurationDays": 30,
                                  "projectFields": ["dev"],
                                  "planningStatus": "detailed plan ready",
                                  "meetingRegion": "seoul mapo",
                                  "workDescription": "Add member operations and notice management features.",
                                  "progressMethod": "remote collaboration",
                                  "techStacks": ["Vue", "Spring"],
                                  "kickoffSchedule": "after meeting"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();

        Long projectId = Long.valueOf(JsonPathUtil.read(
                createResult.getResponse().getContentAsString(),
                "$.data.projectId").toString());

        MockHttpSession developerSession = loginDeveloper();
        MvcResult applyResult = mockMvc.perform(post("/api/projects/{projectId}/applications", projectId)
                        .session(developerSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "employmentType": "outsourcing",
                                  "workDays": 20,
                                  "bidAmount": 3600,
                                  "content": "I can improve the operations workflow in a stable and maintainable way."
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();

        Object applicationId = JsonPathUtil.read(
                applyResult.getResponse().getContentAsString(),
                "$.data.applicationId");

        mockMvc.perform(get("/api/client/projects/{projectId}", projectId).session(clientSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("operations admin feature project"))
                .andExpect(jsonPath("$.data.employmentType").value("outsourcing"))
                .andExpect(jsonPath("$.data.applicationCount").value(1));

        mockMvc.perform(get("/api/client/projects/{projectId}/applicants", projectId)
                        .session(clientSession)
                        .param("page", "1")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.items[0].applicationId").value(applicationId))
                .andExpect(jsonPath("$.data.items[0].developerName").isString())
                .andExpect(jsonPath("$.data.items[0].expectedAmount").value(3600))
                .andExpect(jsonPath("$.data.hasNext").value(false));

        mockMvc.perform(get("/api/client/applications/{applicationId}", applicationId).session(clientSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectId").value(projectId))
                .andExpect(jsonPath("$.data.developerName").isString())
                .andExpect(jsonPath("$.data.workDays").value(20))
                .andExpect(jsonPath("$.data.bidAmount").value(3600))
                .andExpect(jsonPath("$.data.headcount").value(1));
    }

    @Test
    void seededApplicantListAlwaysUsesTwoItemsPerPage() throws Exception {
        MockHttpSession clientSession = loginClient();

        mockMvc.perform(get("/api/client/projects/2/applicants")
                        .session(clientSession)
                        .param("page", "1")
                        .param("size", "99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.items", hasSize(2)))
                .andExpect(jsonPath("$.data.size").value(2))
                .andExpect(jsonPath("$.data.totalItems").value(8))
                .andExpect(jsonPath("$.data.totalPages").value(4))
                .andExpect(jsonPath("$.data.hasNext").value(true));
    }

    @Test
    void developerCannotAccessClientProjectEndpoints() throws Exception {
        MockHttpSession developerSession = loginDeveloper();

        mockMvc.perform(get("/api/client/projects").session(developerSession))
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
}
