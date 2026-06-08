package com.github.term_project.domain.project.repository;

import com.github.term_project.domain.project.entity.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByClient_IdOrderByPostedAtDescIdDesc(Long clientId);

    Optional<Project> findByIdAndClient_Id(Long projectId, Long clientId);

    Optional<Project> findByDisplayOrder(Integer displayOrder);

    long countByClient_Id(Long clientId);
}
