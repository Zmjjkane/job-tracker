package com.zmjjkane.backend.service;

import com.zmjjkane.backend.model.JobApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class JobApplicationService {

    private final List<JobApplication> store = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(4);

    public JobApplicationService() {
        store.add(new JobApplication(1L, "Amazon", "SDE I",
                "APPLIED", LocalDate.now().minusDays(10)));
        store.add(new JobApplication(2L, "NVIDIA", "System Software",
                "INTERVIEW", LocalDate.now().minusDays(25)));
        store.add(new JobApplication(3L, "DJI", "Client-side Dev",
                "OFFER", LocalDate.now().minusDays(40)));
    }

    public JobApplication create(JobApplication input) {
        JobApplication created = new JobApplication(
                idGenerator.getAndIncrement(),
                input.getCompany(),
                input.getPosition(),
                input.getStatus(),
                input.getAppliedDate()
        );

        store.add(created);
        return created;
    }

    // Mock data for now (no DB)
    public List<JobApplication> listAll() {
        return store;
    }
}
