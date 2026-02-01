package com.zmjjkane.backend.service;

import com.zmjjkane.backend.model.JobApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobApplicationService {

    // Mock data for now (no DB)
    public List<JobApplication> listAll() {
        return List.of(
                new JobApplication(1L, "Amazon", "SDE I",
                        "APPLIED", LocalDate.now().minusDays(10)),
                new JobApplication(2L, "NVIDIA", "System Software",
                        "INTERVIEW", LocalDate.now().minusDays(25)),
                new JobApplication(3L, "DJI", "Client-side Dev",
                        "OFFER", LocalDate.now().minusDays(40))
        );
    }
}
