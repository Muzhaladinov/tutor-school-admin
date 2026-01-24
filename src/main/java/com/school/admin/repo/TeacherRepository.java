package com.school.admin.repo;

import com.school.admin.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findTop200ByFullNameContainingIgnoreCaseOrderByIdDesc(String q);
    List<Teacher> findTop200ByOrderByIdDesc();
}