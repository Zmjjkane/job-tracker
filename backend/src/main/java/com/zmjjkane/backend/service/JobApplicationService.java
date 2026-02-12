package com.zmjjkane.backend.service;

import com.zmjjkane.backend.model.JobApplication;
import com.zmjjkane.backend.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
        return repository.findById(id).orElse(null);
    }

    public JobApplication updateById(Long id, JobApplication input) {
        JobApplication updated = getById(id);
        if  (updated == null) {
            return null;
        }
        updated.setCompany(input.getCompany());
        updated.setPosition(input.getPosition());
        updated.setStatus(input.getStatus());
        updated.setAppliedDate(input.getAppliedDate());
        return repository.save(updated);
    }

    public boolean deleteById(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    // Mock data for now (no DB)
    public List<JobApplication> listAll() {
        return repository.findAll();
    }
}
