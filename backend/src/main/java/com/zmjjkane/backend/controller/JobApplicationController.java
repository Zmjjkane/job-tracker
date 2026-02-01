package com.zmjjkane.backend.controller;

import com.zmjjkane.backend.model.JobApplication;
import com.zmjjkane.backend.service.JobApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    // GET /api.job-applications -> returns mock list
    @GetMapping("/api/job-applications")
    public List<JobApplication> listAll() {
        return jobApplicationService.listAll();
    }
}
