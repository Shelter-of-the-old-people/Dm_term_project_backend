package com.github.term_project.domain.application.entity;

import com.github.term_project.domain.project.entity.EmploymentType;
import com.github.term_project.domain.project.entity.Project;
import com.github.term_project.domain.user.entity.User;
import com.github.term_project.global.common.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
        name = "applications",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_application_project_developer",
                columnNames = {"project_id", "developer_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id", nullable = false)
    private User developer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmploymentType employmentType;

    @Column
    private Integer workDays;

    @Column
    private Integer bidAmount;

    @Column(nullable = false, length = 2000)
    private String content;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder asc, id asc")
    private List<ApplicationOnsiteLine> onsiteLines = new ArrayList<>();

    @Builder
    public Application(
            Project project,
            User developer,
            EmploymentType employmentType,
            Integer workDays,
            Integer bidAmount,
            String content,
            List<ApplicationOnsiteLine> onsiteLines) {
        this.project = project;
        this.developer = developer;
        this.employmentType = employmentType;
        this.workDays = workDays;
        this.bidAmount = bidAmount;
        this.content = content;

        if (onsiteLines != null) {
            onsiteLines.forEach(this::addOnsiteLine);
        }
    }

    public Integer resolveEstimateAmount() {
        if (employmentType == EmploymentType.OUTSOURCING) {
            return bidAmount;
        }
        if (onsiteLines.isEmpty()) {
            return null;
        }
        return onsiteLines.get(0).getMonthlyPay();
    }

    private void addOnsiteLine(ApplicationOnsiteLine onsiteLine) {
        onsiteLine.assignApplication(this);
        this.onsiteLines.add(onsiteLine);
    }
}
