package com.zmjjkane.backend.service;

import com.zmjjkane.backend.exception.ResourceNotFoundException;
import com.zmjjkane.backend.model.ApplicationStatus;
import com.zmjjkane.backend.model.JobApplication;
import com.zmjjkane.backend.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public JobApplication create(JobApplication input) {
        input.setId(null);
        return repository.save(input);
    }

    public JobApplication getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication not found: " + id));
    }

    public JobApplication updateById(Long id, JobApplication input) {
        JobApplication updated = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobApplication not found: " + id));

        updated.setCompany(input.getCompany());
        updated.setPosition(input.getPosition());
        updated.setStatus(input.getStatus());
        updated.setAppliedDate(input.getAppliedDate());
        return repository.save(updated);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("JobApplication not found: " + id);
        }
        repository.deleteById(id);
    }

    // Mock data for now (no DB)
    public List<JobApplication> listAll() {
        return repository.findAll();
    }

    public List<JobApplication> listByStatus(ApplicationStatus status) {
        return repository.findByStatus(status);
    }
}
