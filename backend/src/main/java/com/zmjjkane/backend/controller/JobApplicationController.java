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

    // GET /api.job-applications -> returns mock list
    @GetMapping
    public List<JobApplication> listAll() {
        return jobApplicationService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getById(@PathVariable Long id) {
        // 通过ResponseEntity我们可以定义返回的状态
        
        JobApplication jobApplication = jobApplicationService.getById(id);

        if (jobApplication == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(jobApplication);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplication create(@RequestBody JobApplication job) {
        return jobApplicationService.create(job);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = jobApplicationService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
