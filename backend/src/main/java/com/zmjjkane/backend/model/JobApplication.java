package com.zmjjkane.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeId;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.*;
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
    private String company;
    private String position;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appliedDate;
    public JobApplication() {
        // Required by Jackson for JSON -> object
    }

    // Optional convenience constructor
    public JobApplication(Long id, String company, String position, String status, LocalDate appliedDate) {
        this.id = id;
        this.company = company;
        this.position = position;
        this.status = status;
        this.appliedDate = appliedDate;
    }

    public Long getId() { return id; }
    public String getCompany() { return company; }
    public String getPosition() { return position; }
    public String getStatus() { return status; }
    public LocalDate getAppliedDate() { return appliedDate; }

    public void setId(Long id) { this.id = id; }
    public void setCompany(String company) { this.company = company; }
    public void setPosition(String position) { this.position = position; }
    public void setStatus(String status) { this.status = status; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }
}
