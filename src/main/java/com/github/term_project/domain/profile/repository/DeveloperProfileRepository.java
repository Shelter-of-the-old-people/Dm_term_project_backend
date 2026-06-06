package com.github.term_project.domain.profile.repository;

import com.github.term_project.domain.profile.entity.DeveloperProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperProfileRepository extends JpaRepository<DeveloperProfile, Long> {

    @EntityGraph(attributePaths = {"supportFields", "searchTags"})
    Optional<DeveloperProfile> findByUser_Id(Long userId);
}
