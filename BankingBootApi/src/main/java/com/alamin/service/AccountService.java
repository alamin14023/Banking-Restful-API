package com.alamin.service;

import java.util.List;

import com.alamin.dto.AccountDto;

public interface AccountService {

	String createAccount(AccountDto accountDto);
	
	String loginAccount(String username, String password);

	AccountDto getUserAccount();
	
	AccountDto deposit(Long id, double amount);
	
	AccountDto withdraw(Long id, double amount);
	
	AccountDto getAccountById(Long id);
	
	List<AccountDto> getAllAccounts();
	
	void deleteAccount(Long id);
}
