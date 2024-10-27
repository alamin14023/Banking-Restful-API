package com.alamin.service.impl;

import java.util.NoSuchElementException;

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

		Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found."));

		return AccountMapper.mapToAccountDto(account);
	}

}
