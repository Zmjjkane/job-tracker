package com.zmjjkane.backend.controller;

import com.zmjjkane.backend.model.JobApplication;
import com.zmjjkane.backend.service.JobApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
        return ResponseEntity.ok(jobApplication);
    }

    @PostMapping
    public ResponseEntity<JobApplication> create(@Valid @RequestBody JobApplication job) {
        // if need to return body, use .body, .ok(body) is a special case
        // if no body return, use .build()
        // @Valid是触发器, 告诉Spring对这个参数对象做bean validation
        // @RequestBody -> 把JSON转成JobApplication, @Valid -> 触发校验
        // 如果违反@NotBlank/@NotNull/... -> 不进入controller方法体, 走异常流程
        JobApplication jobApplication = jobApplicationService.create(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> update(
            @PathVariable Long id, @Valid @RequestBody JobApplication job) {
        JobApplication jobApplication = jobApplicationService.updateById(id, job);
        return ResponseEntity.ok(jobApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobApplicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
