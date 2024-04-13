package io.netstacker.latte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.netstacker.latte.model.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>{}
