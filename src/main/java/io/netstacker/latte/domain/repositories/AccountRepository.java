package io.netstacker.latte.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.netstacker.latte.domain.models.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    @Query("SELECT s FROM Account s WHERE s.email = ?1")
    Optional<Account> findAccountByEmail(String email);
}
