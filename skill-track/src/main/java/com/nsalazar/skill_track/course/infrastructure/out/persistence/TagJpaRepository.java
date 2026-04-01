package com.nsalazar.skill_track.course.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for {@link TagJpaEntity}.
 */
public interface TagJpaRepository extends JpaRepository<TagJpaEntity, UUID> {

    Optional<TagJpaEntity> findByName(String name);

    boolean existsByName(String name);
}
