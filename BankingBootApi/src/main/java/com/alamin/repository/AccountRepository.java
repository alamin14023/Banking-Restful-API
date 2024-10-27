package com.alamin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alamin.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByUsername(String username);
	Boolean existsByUsername(String username); 
}
