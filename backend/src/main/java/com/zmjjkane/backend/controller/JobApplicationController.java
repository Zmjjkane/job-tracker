package com.zmjjkane.backend.controller;

import com.zmjjkane.backend.model.JobApplication;
import com.zmjjkane.backend.service.JobApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    // GET /api.job-applications -> returns mock list
    @GetMapping
    public List<JobApplication> listAll() {
        return jobApplicationService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplication create(@RequestBody JobApplication job) {
        return jobApplicationService.create(job);
    }
}
