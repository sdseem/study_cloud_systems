package com.example.study_cloud_systems.repositories;

import com.example.study_cloud_systems.dto.entity.NumbersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface NumbersRepository extends JpaRepository<NumbersEntity, Long> {
    Integer countAllByIdIn(Set<Long> ids);

    default boolean allExistsByIds(Set<Long> ids) {
        Integer count = countAllByIdIn(ids);
        return count != null && count.equals(ids.size());
    }
}