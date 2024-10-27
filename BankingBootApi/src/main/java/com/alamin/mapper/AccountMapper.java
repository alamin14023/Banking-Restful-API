package com.alamin.mapper;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.alamin.dto.AccountDto;
import com.alamin.entity.Account;
import com.alamin.entity.Role;

@Component
public class AccountMapper {
	
	public static Account mapToAccount (AccountDto accountDto, Role roles) {
		Account account = new Account();
		        account.setUsername(accountDto.getUsername());
		        account.setPassword(accountDto.getPassword());
				account.setAccountHolderName(accountDto.getAccountHolderName());
				account.setBalance(accountDto.getBalance());
				account.setRoles(Collections.singleton(roles));
				
		return account;
	}
	public static AccountDto mapToAccountDto (Account account) {
		AccountDto accountDto = new AccountDto();
				accountDto.setId(account.getId());
				accountDto.setUsername(account.getUsername());
				accountDto.setAccountHolderName(account.getAccountHolderName());
				accountDto.setBalance(account.getBalance());
				
		return accountDto;
	}

}
