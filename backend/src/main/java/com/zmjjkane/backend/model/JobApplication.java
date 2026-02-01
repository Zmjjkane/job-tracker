package com.zmjjkane.backend.model;

import java.time.LocalDate;

public class JobApplication {
    private long id;
    private String company;
    private String position;
    private String status;
    private LocalDate appliedDate;
    public JobApplication() {}

    public JobApplication(long id, String company, String position, String status, LocalDate appliedDate) {
        this.id = id;
        this.company = company;
        this.position = position;
        this.status = status;
        this.appliedDate = appliedDate;
    }

    public long getId() { return id; }
    public String getCompany() { return company; }
    public String getPosition() { return position; }
    public String getStatus() { return status; }
    public LocalDate getAppliedDate() { return appliedDate; }

    public void setId(long id) { this.id = id; }
    public void setCompany(String company) { this.company = company; }
    public void setPosition(String position) { this.position = position; }
    public void setStatus(String status) { this.status = status; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }
}
