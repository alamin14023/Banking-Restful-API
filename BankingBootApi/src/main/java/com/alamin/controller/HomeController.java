package com.alamin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alamin.dto.AccountDto;
import com.alamin.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/accounts")
public class HomeController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/register")
	public ResponseEntity<String> addAccount(@RequestBody AccountDto accountDto) {
		String result = accountService.createAccount(accountDto);
		if (result.startsWith("Error")) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	//no use
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AccountDto accountDto){
		String result = accountService.loginAccount(accountDto.getUsername(),accountDto.getPassword());
		
		if(result.startsWith("Error")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
		}
		
		return ResponseEntity.ok(result);
	}
	
	//no use
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
		if(authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
			return ResponseEntity.ok("Successfully logged out.");
		}
		
		return ResponseEntity.ok("No user is logged in.");
	}

}
