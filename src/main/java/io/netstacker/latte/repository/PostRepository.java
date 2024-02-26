package io.netstacker.latte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.netstacker.latte.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{}
