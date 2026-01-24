package com.school.admin.repo;

import com.school.admin.domain.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    @Query("""
        select distinct g
        from GroupEntity g
        left join fetch g.teacher t
        left join fetch g.students s
        order by g.id desc
    """)
    List<GroupEntity> findTop200Full();

    @Query("""
        select distinct g
        from GroupEntity g
        left join fetch g.teacher t
        left join fetch g.students s
        where lower(g.name) like lower(concat('%', :q, '%'))
        order by g.id desc
    """)
    List<GroupEntity> findTop200FullByName(@Param("q") String q);

    @Query("""
        select distinct g
        from GroupEntity g
        left join fetch g.teacher t
        left join fetch g.students s
        where g.id = :id
    """)
    Optional<GroupEntity> findByIdFull(@Param("id") Long id);
}