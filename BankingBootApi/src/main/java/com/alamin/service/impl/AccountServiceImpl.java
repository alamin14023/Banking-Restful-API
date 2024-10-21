package com.alamin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alamin.dto.AccountDto;
import com.alamin.entity.Account;
import com.alamin.mapper.AccountMapper;
import com.alamin.repository.AccountRepository;
import com.alamin.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public AccountDto createAccount(AccountDto accountDto) {

		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = accountRepository.save(account);

		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto getAccountById(Long id) {

		Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found."));

		return AccountMapper.mapToAccountDto(account);
	}

}
