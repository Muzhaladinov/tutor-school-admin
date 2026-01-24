package com.school.admin.service;

import com.school.admin.domain.Teacher;
import com.school.admin.repo.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository repo;

    public TeacherService(TeacherRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Teacher> list(String q) {
        if (q == null || q.isBlank()) return repo.findTop200ByOrderByIdDesc();
        return repo.findTop200ByFullNameContainingIgnoreCaseOrderByIdDesc(q.trim());
    }

    @Transactional(readOnly = true)
    public Teacher getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Teacher not found: " + id));
    }

    @Transactional
    public Teacher save(Teacher t) {
        if (t.getRateRub() == null) t.setRateRub(0);
        if (t.getRateRub() < 0) t.setRateRub(0);
        return repo.save(t);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<Teacher> listTop200() {
        return repo.findTop200ByOrderByIdDesc();
    }
}