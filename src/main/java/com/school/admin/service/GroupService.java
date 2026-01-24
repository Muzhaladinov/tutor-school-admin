package com.school.admin.service;

import com.school.admin.domain.GroupEntity;
import com.school.admin.repo.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository repo;

    public GroupService(GroupRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<GroupEntity> list(String q) {
        if (q == null || q.isBlank()) return repo.findTop200Full();
        return repo.findTop200FullByName(q.trim());
    }

    @Transactional(readOnly = true)
    public GroupEntity getOrThrow(Long id) {
        return repo.findByIdFull(id).orElseThrow(() -> new IllegalArgumentException("Group not found: " + id));
    }

    @Transactional
    public GroupEntity save(GroupEntity g) {
        if (g.getPriceRub() == null || g.getPriceRub() < 0) g.setPriceRub(0);
        return repo.save(g);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}