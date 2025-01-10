package io.netstacker.latte.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.netstacker.latte.domain.models.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile>{}
