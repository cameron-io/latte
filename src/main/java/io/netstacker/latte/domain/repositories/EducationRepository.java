package io.netstacker.latte.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.netstacker.latte.domain.models.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long>{}
