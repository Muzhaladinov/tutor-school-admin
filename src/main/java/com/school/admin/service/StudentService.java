package com.school.admin.service;

import com.school.admin.domain.Student;
import com.school.admin.repo.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Student> list(String q) {
        if (q == null || q.isBlank()) {
            return repo.findTop200ByOrderByIdDesc();
        }
        return repo.findTop200ByFullNameContainingIgnoreCaseOrderByIdDesc(q.trim());
    }

    @Transactional(readOnly = true)
    public Student getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
    }

    @Transactional
    public Student save(Student s) {
        return repo.save(s);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<Student> listTop200() {
        return repo.findTop200ByOrderByIdDesc();
    }
}