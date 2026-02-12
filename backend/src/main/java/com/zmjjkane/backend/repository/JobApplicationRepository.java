package com.zmjjkane.backend.repository;

import com.zmjjkane.backend.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JobApplicationRepository
 *
 * This interface extends JpaRepository.
 *
 * Spring automatically generates the implementation
 * at runtime using dynamic proxy.
 *
 * We DO NOT need to implement CRUD methods manually.
 *
 * JpaRepository provides:
 * - save()
 * - findAll()
 * - findById()
 * - existsById()
 * - deleteById()
 *
 * Generic parameters:
 * <EntityType, IdType>
 *
 * IdType MUST match the primary key type in Entity.
 * Here we use Long.
 */

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
}
