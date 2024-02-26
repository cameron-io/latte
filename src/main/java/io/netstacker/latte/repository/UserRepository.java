package io.netstacker.latte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import io.netstacker.latte.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query("SELECT u WHERE User u WHERE u.email = ?1")
    Optional<User> findUserByEmail(String email);
}
