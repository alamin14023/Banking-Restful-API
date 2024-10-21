package com.alamin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alamin.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
