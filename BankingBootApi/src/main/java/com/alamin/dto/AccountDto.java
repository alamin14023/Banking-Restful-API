package com.alamin.dto;

import lombok.Data;

@Data
public class AccountDto {
	private Long id;
	private String username;
	private String password;
	private String accountHolderName;
	private double balance;

}

