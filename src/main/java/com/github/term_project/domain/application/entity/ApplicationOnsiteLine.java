package com.github.term_project.domain.application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "application_onsite_lines")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationOnsiteLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(nullable = false, length = 60)
    private String skillCategory;

    @Column(nullable = false, length = 40)
    private String careerLevel;

    @Column(nullable = false)
    private Integer headCount;

    @Column(nullable = false)
    private Integer monthlyPay;

    @Column(nullable = false)
    private Integer sortOrder;

    @Builder
    public ApplicationOnsiteLine(
            String skillCategory,
            String careerLevel,
            Integer headCount,
            Integer monthlyPay,
            Integer sortOrder) {
        this.skillCategory = skillCategory;
        this.careerLevel = careerLevel;
        this.headCount = headCount;
        this.monthlyPay = monthlyPay;
        this.sortOrder = sortOrder;
    }

    void assignApplication(Application application) {
        this.application = application;
    }
}
