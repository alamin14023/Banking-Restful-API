package com.alamin.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alamin.dto.AccountDto;
import com.alamin.entity.Account;
import com.alamin.entity.Role;
import com.alamin.mapper.AccountMapper;
import com.alamin.repository.AccountRepository;
import com.alamin.repository.RoleRepository;
import com.alamin.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public String loginAccount(String username, String password) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return "Login successful for user : "+ username;
		}catch (Exception e) {
			// TODO: handle exception
			return "Error: Invalid username or password.";
		}
	}

	@Override
	public String createAccount(AccountDto accountDto) {
		if(accountRepository.existsByUsername(accountDto.getUsername())) {
			return "Error: Username is already taken!";
		}
		Role roles = roleRepository.findByName("USER").orElseThrow(() -> new NoSuchElementException("Not found"));
		Account account = AccountMapper.mapToAccount(accountDto, roles);
		account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		accountRepository.save(account);

		return "User registered successfully!";
	}

	@Override
	public AccountDto getAccountById(Long id) {

		Account account = accountRepository
				.findById(id)
				.orElseThrow(() -> new RuntimeException("Account not found."));

		return AccountMapper.mapToAccountDto(account);
	}

	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account = accountRepository
				.findById(id)
				.orElseThrow(() -> new RuntimeException("Account not found."));
		
		double total = account.getBalance() + amount;
		account.setBalance(total);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto withdraw(double amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String username = authentication.getName();
		Account account = accountRepository
				.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Account not found."));
		
		if(account.getBalance()< amount) {
			throw new RuntimeException("Insufficient balance.");
		}
		double total = account.getBalance() - amount;
		account.setBalance(total);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAccount(Long id) {
		accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account not exists."));
		
		accountRepository.deleteById(id);
	}

	@Override
	public AccountDto getUserAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String username = authentication.getName();
		Account account = accountRepository
				.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Account not found."));

		return AccountMapper.mapToAccountDto(account);
	}

}
