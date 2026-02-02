package com.zmjjkane.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class JobApplication {
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
