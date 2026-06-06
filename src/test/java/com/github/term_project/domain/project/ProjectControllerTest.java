package com.github.term_project.domain.project;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:project-api-test;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProjectsReturnsFirstPageWithFourItems() throws Exception {
        mockMvc.perform(get("/api/projects")
                        .param("page", "1")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.items", hasSize(4)))
                .andExpect(jsonPath("$.data.page").value(1))
                .andExpect(jsonPath("$.data.size").value(4))
                .andExpect(jsonPath("$.data.totalItems").value(12))
                .andExpect(jsonPath("$.data.totalPages").value(3))
                .andExpect(jsonPath("$.data.hasNext").value(true));
    }

    @Test
    void getProjectsFiltersResidentType() throws Exception {
        mockMvc.perform(get("/api/projects")
                        .param("type", "resident")
                        .param("page", "1")
                        .param("size", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.items", hasSize(4)))
                .andExpect(jsonPath("$.data.totalItems").value(5))
                .andExpect(jsonPath("$.data.items[0].employmentType").value("resident"))
                .andExpect(jsonPath("$.data.items[1].employmentType").value("resident"));
    }

    @Test
    void getProjectsSortsByDeadlineAscending() throws Exception {
        mockMvc.perform(get("/api/projects")
                        .param("sort", "deadline")
                        .param("page", "1")
                        .param("size", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.items[0].title").value("병원 예약 키오스크 화면 개발"))
                .andExpect(jsonPath("$.data.items[1].title").value("B2B SaaS 디자인 시스템 운영"))
                .andExpect(jsonPath("$.data.items[0].deadlineLabel").value("D-3"));
    }

    @Test
    void getProjectReturnsDetail() throws Exception {
        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.employmentType").value("outsourcing"))
                .andExpect(jsonPath("$.data.categories", hasSize(3)))
                .andExpect(jsonPath("$.data.skills", hasSize(3)))
                .andExpect(jsonPath("$.data.summary").value("프리모아 메인 레퍼런스를 참고해 신뢰감 있는 기업형 사이트를 완성하는 프로젝트입니다."));
    }
}
