package com.zmjjkane.backend.controller;

import com.zmjjkane.backend.model.JobApplication;
import com.zmjjkane.backend.service.JobApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    // GET /api/job-applications -> returns list
    @GetMapping
    public List<JobApplication> listAll() {
        return jobApplicationService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getById(@PathVariable Long id) {
        // Use ResponseEntity to control HTTP status codes

        JobApplication jobApplication = jobApplicationService.getById(id);

        if (jobApplication == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(jobApplication);
    }

    @PostMapping
    public ResponseEntity<JobApplication> create(@RequestBody JobApplication job) {
        // if need to return body, use .body, .ok(body) is a special case
        // if no body return, use .build()
        JobApplication jobApplication = jobApplicationService.create(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> update(
            @PathVariable Long id, @RequestBody JobApplication job) {
        JobApplication jobApplication = jobApplicationService.updateById(id, job);
        if (jobApplication == null) {
            // not found -> 404
            return ResponseEntity.notFound().build();
        }
        // ok -> 200 + json
        return ResponseEntity.ok(jobApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = jobApplicationService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
