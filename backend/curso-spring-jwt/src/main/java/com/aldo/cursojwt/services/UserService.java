package com.aldo.cursojwt.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.aldo.cursojwt.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		
		try {
			
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch (Exception e) {
			return null;
		}
	}

}
