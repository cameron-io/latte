package io.netstacker.latte.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.netstacker.latte.domain.models.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>{}
