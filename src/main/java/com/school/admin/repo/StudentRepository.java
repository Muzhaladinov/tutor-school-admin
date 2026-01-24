package com.school.admin.repo;

import com.school.admin.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findTop200ByFullNameContainingIgnoreCaseOrderByIdDesc(String q);
    List<Student> findTop200ByOrderByIdDesc();
}