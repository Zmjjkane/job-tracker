package com.zmjjkane.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmjjkane.backend.model.JobApplication;
import com.zmjjkane.backend.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Autowired
    private ObjectMapper objectMapper;

    // Mock service dependency for controller isolation in @WebMvcTest.
    @MockBean
    private JobApplicationService jobApplicationService;

    @Test
    void listAll_returns200() throws Exception {
        // Stub the service to return a small list.
        when(jobApplicationService.listAll()).thenReturn(
                List.of(new JobApplication(1L, "Amazon", "SDE I", "APPLIED", LocalDate.now()))
        );

        // Verify: HTTP 200 and response if JSON
        mockMvc.perform(get("/api/job-applications"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void create_returns201_andBody() throws  Exception {
        JobApplication request = new JobApplication(
                null, "Google", "SWE", "APPLIED", LocalDate.of(2026, 2, 9)
        );
        JobApplication created = new JobApplication(
                10L, "Google", "SWE", "APPLIED", LocalDate.of(2026, 2, 9)
        );

        // Stub: regardless of input object, service.create(...) returns "created".
        when(jobApplicationService.create(any(JobApplication.class))).thenReturn(created);

        // perform() tells the request method and url
        mockMvc.perform(post("/api/job-applications")
                // use json as the content type to send the request
                .contentType(MediaType.APPLICATION_JSON)
                // write an object as a json format and put it into a request body to send request
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.company").value("Google"))
                .andExpect(jsonPath("$.status").value("APPLIED"));
    }

    @Test
    void update_nofFound_returns404() throws Exception {
        JobApplication request = new JobApplication(
                null, "Amazon", "SDE I", "INTERVIEW", LocalDate.of(2026, 2, 9)
        );

        // Stub: service returns null -> controller should map it to 404.
        when(jobApplicationService.updateById(eq(999L), any(JobApplication.class))).thenReturn(null);

        mockMvc.perform(put("/api/job-applications/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_success_returns200_andBody() throws  Exception {
        JobApplication request = new JobApplication(
                null, "Amazon", "SDE I", "INTERVIEW", LocalDate.of(2026, 2, 9)
        );
        JobApplication updated = new JobApplication(
                1L, "Amazon", "SDE I", "INTERVIEW", LocalDate.of(2026, 2, 9)
        );
        when(jobApplicationService.updateById(any(Long.class), any(JobApplication.class))).thenReturn(updated);

        mockMvc.perform(put("/api/job-applications/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.company").value("Amazon"))
                .andExpect(jsonPath("$.status").value("INTERVIEW"));
    }

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
