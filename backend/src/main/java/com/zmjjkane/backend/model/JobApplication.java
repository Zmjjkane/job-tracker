package com.zmjjkane.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


/**
 * JobApplication entity class.
 *
 * This class represents a database table "job_applications".
 *
 * We are using Spring Data JPA with Hibernate.
 *
 * IMPORTANT:
 * We are using Code-First approach.
 *
 * That means:
 * - Table structure is defined by this Java class.
 * - Hibernate automatically generates or updates the table
 *   based on entity definition.
 *
 * This behavior is controlled by:
 * spring.jpa.hibernate.ddl-auto=update
 *
 * If the table does not exist → Hibernate creates it.
 * If new fields are added → Hibernate updates table.
 *
 * Primary key type is Long.
 * It must match the ID type in Repository.
 */

@Entity
@Table(name="job_applications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 属于Bean Validation, 只在触发校验时才会执行
    // 常见触发: 1. Controller入参上触发, 配合@Valid
    // 2. 持久化/JPA阶段触发, 但更推荐controller入参时拦截
    @NotBlank(message = "company is required")
    private String company;

    @NotBlank(message = "position is required")
    private String position;

    /**
     * Application status represented as an enum to enforce a fixed set of
     * valid states across the frontend–backend API contract.
     *
     * JSON → enum conversion is handled automatically by Jackson during
     * request deserialization (e.g., "APPLIED" → ApplicationStatus.APPLIED).
     * Invalid or misspelled values will cause a 400 Bad Request, ensuring
     * clients send only supported status values.
     *
     * @Enumerated(EnumType.STRING) controls how the enum is stored in the
     * database (as its name, e.g., "APPLIED") and read back into the enum.
     * It does NOT participate in JSON conversion.
     *
     * Using enum here guarantees consistent status semantics for filtering,
     * analytics, and Kanban logic, and prevents arbitrary or inconsistent
     * status strings in the system.
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "status is required")
    private ApplicationStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "appliedDate is required")
    private LocalDate appliedDate;
    public JobApplication() {
        // Required by Jackson for JSON -> object
    }

    // Optional convenience constructor
    public JobApplication(Long id, String company, String position, ApplicationStatus status, LocalDate appliedDate) {
        this.id = id;
        this.company = company;
        this.position = position;
        this.status = status;
        this.appliedDate = appliedDate;
    }

    public Long getId() { return id; }
    public String getCompany() { return company; }
    public String getPosition() { return position; }
    public ApplicationStatus getStatus() { return status; }
    public LocalDate getAppliedDate() { return appliedDate; }

    public void setId(Long id) { this.id = id; }
    public void setCompany(String company) { this.company = company; }
    public void setPosition(String position) { this.position = position; }
    public void setStatus(ApplicationStatus status) { this.status = status; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }
}
