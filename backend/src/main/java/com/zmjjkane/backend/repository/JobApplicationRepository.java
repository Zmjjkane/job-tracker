package com.zmjjkane.backend.repository;

import com.zmjjkane.backend.model.ApplicationStatus;
import com.zmjjkane.backend.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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

// JpaRepository只给了通用CRUD, 没有给按字段过滤的方法, 在此声明后
// Spring Data JPA会根据方法名里的规则自动生成SQL, 不用自己写实现
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByStatus(ApplicationStatus status);
}
