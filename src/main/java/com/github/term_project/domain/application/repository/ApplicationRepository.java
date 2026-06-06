package com.github.term_project.domain.application.repository;

import com.github.term_project.domain.application.entity.Application;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByProject_IdAndDeveloper_Id(Long projectId, Long developerId);

    @EntityGraph(attributePaths = {"project", "onsiteLines"})
    List<Application> findAllByDeveloper_IdOrderByCreatedAtDesc(Long developerId);

    @EntityGraph(attributePaths = {"project", "onsiteLines"})
    Optional<Application> findByIdAndDeveloper_Id(Long applicationId, Long developerId);

    @EntityGraph(attributePaths = {"developer", "onsiteLines"})
    List<Application> findAllByProject_IdOrderByCreatedAtDesc(Long projectId);

    @EntityGraph(attributePaths = {"project", "developer", "onsiteLines"})
    Optional<Application> findByIdAndProject_Client_Id(Long applicationId, Long clientId);
}
