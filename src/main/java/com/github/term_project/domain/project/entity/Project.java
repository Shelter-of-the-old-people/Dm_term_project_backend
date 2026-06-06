package com.github.term_project.domain.project.entity;

import com.github.term_project.global.common.BaseTimeEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "projects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 80)
    private String area;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RecruitStatus recruitStatus;

    @Column
    private Integer budgetMin;

    @Column
    private Integer budgetMax;

    @Column
    private Integer monthlyWage;

    @Column(nullable = false)
    private Integer expectedDurationDays;

    @Column(nullable = false)
    private Integer applicationCount;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private LocalDate postedAt;

    @Column(nullable = false)
    private Integer displayOrder;

    @Column(nullable = false, length = 80)
    private String kickoffSchedule;

    @Column(nullable = false, length = 80)
    private String progressType;

    @Column(nullable = false, length = 80)
    private String planningStatus;

    @Column(nullable = false, length = 80)
    private String meetingLocation;

    @Column(nullable = false, length = 2000)
    private String workDescription;

    @Column(nullable = false, length = 1000)
    private String workMethod;

    @Column(nullable = false, length = 1000)
    private String summary;

    @ElementCollection
    @CollectionTable(name = "project_categories", joinColumns = @JoinColumn(name = "project_id"))
    @OrderColumn(name = "category_order")
    @Column(name = "category_name", nullable = false, length = 30)
    private List<String> categories = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "project_skills", joinColumns = @JoinColumn(name = "project_id"))
    @OrderColumn(name = "skill_order")
    @Column(name = "skill_name", nullable = false, length = 50)
    private List<String> skills = new ArrayList<>();

    @Builder
    public Project(
            String title,
            String area,
            EmploymentType employmentType,
            RecruitStatus recruitStatus,
            Integer budgetMin,
            Integer budgetMax,
            Integer monthlyWage,
            Integer expectedDurationDays,
            Integer applicationCount,
            LocalDate deadline,
            LocalDate postedAt,
            Integer displayOrder,
            String kickoffSchedule,
            String progressType,
            String planningStatus,
            String meetingLocation,
            String workDescription,
            String workMethod,
            String summary,
            List<String> categories,
            List<String> skills) {
        this.title = title;
        this.area = area;
        this.employmentType = employmentType;
        this.recruitStatus = recruitStatus;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.monthlyWage = monthlyWage;
        this.expectedDurationDays = expectedDurationDays;
        this.applicationCount = applicationCount;
        this.deadline = deadline;
        this.postedAt = postedAt;
        this.displayOrder = displayOrder;
        this.kickoffSchedule = kickoffSchedule;
        this.progressType = progressType;
        this.planningStatus = planningStatus;
        this.meetingLocation = meetingLocation;
        this.workDescription = workDescription;
        this.workMethod = workMethod;
        this.summary = summary;
        this.categories = new ArrayList<>(categories);
        this.skills = new ArrayList<>(skills);
    }
}
