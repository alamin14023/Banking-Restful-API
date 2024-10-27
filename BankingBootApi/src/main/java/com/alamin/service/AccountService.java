package com.alamin.service;

import com.alamin.dto.AccountDto;

public interface AccountService {

	String createAccount(AccountDto accountDto);

	AccountDto getAccountById(Long id);
	
	String loginAccount(String username, String password);
}
