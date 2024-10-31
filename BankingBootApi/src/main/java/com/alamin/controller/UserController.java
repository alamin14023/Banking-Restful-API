package com.alamin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alamin.dto.AccountDto;
import com.alamin.service.AccountService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	AccountService accountService;

	@GetMapping("/showAccount")
	public ResponseEntity<AccountDto> geUsertAccount() {
		AccountDto accountDto = accountService.getUserAccount();
		return ResponseEntity.ok(accountDto);
	}
	
	@PutMapping("/deposit/{id}")
	public ResponseEntity<AccountDto> deposit(@PathVariable Long id,
			@RequestBody Map<String, Double> request){
		
		Double amount = request.get("amount");
		AccountDto accountDto = accountService.deposit(id, amount);
		return ResponseEntity.ok(accountDto);
	}
	
	@PutMapping("/withdraw/{id}")
	public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
			@RequestBody Map<String, Double> request){
		
		Double amount = request.get("amount");
		AccountDto accountDto = accountService.withdraw(id, amount);
		return ResponseEntity.ok(accountDto);
	}
}
