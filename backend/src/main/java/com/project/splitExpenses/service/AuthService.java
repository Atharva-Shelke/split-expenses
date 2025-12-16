package com.project.splitExpenses.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;

public interface AuthService {
	Map<String, Object> login(String user, String password);
	
	ResponseEntity<Map<String, Object>> checkSession(HttpSession session);

}
