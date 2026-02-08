package com.zmjjkane.backend.controller;

import com.zmjjkane.backend.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Controller layer tests (HTTP semantics only).
 *
 * - @WebMvcTest starts a minimal Spring MVC context and loads the target controller.
 * - The real service layer is NOT started; we use @MockBean to inject a Mockito mock
 *   so we can control service outputs and test only controller behavior.
 * - MockMvc simulates HTTP requests without running a real server (Tomcat).
 *
 * Pattern per test:
 * 1) Stub service result via Mockito when(...).thenReturn(...)
 * 2) Perform an HTTP request via MockMvc
 * 3) Assert the resulting HTTP status code (REST behavior)
 */
@WebMvcTest(JobApplicationController.class)
public class JobApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock service dependency for controller isolation in @WebMvcTest.
    @MockBean
    private JobApplicationService jobApplicationService;

    @Test
    void getById_notFound_returns404() throws Exception {
        // Simulate "not found" at the service layer.
        when(jobApplicationService.getById(999L)).thenReturn(null);

        // GET /api/job-applications/999 -> 404 Not Found
        mockMvc.perform(get("/api/job-applications/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById_notFound_returns404() throws Exception {
        // Simulate failed deletion (resource not found).
        when(jobApplicationService.deleteById(999L)).thenReturn(false);

        // DELETE /api/job-applications/999 -> 404 Not Found
        mockMvc.perform(delete("/api/job-applications/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_success_returns204() throws Exception {
        // Simulate successful deletion.
        when(jobApplicationService.deleteById(1L)).thenReturn(true);

        // DELETE /api/job-applications/1 -> 204 No Content
        mockMvc.perform(delete("/api/job-applications/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_thenGet_returns404() throws Exception {
        /*
         * Post-delete behavior simulation:
         * - delete succeeds
         * - subsequent lookup returns null
         *
         * Note: service is mocked, so we verify controller HTTP behavior,
         * not actual persistence/state changes.
         */
        when(jobApplicationService.deleteById(1L)).thenReturn(true);
        when(jobApplicationService.getById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/job-applications/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/job-applications/1"))
                .andExpect(status().isNotFound());
    }
}
