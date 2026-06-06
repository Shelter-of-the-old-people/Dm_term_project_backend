package com.github.term_project.domain.project.entity;

public enum EmploymentType {
    OUTSOURCING,
    RESIDENT;

    public String toApiValue() {
        return name().toLowerCase();
    }
}
