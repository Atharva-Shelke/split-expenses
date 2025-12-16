package com.project.splitExpenses.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.splitExpenses.dao.UserDao;
import com.project.splitExpenses.util.Constants;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Autowired
	private UserDao userDao;

	@Override
	public Map<String, Object> login(String user, String password) {
		logger.debug("login user: {}, password: {}", user, password);

		return userDao.login(user, password);
	}

	@Override
	public ResponseEntity<Map<String, Object>> checkSession(HttpSession session) {
	    Long userId = (Long) session.getAttribute(Constants.Param.userId);
	    String username = (String) session.getAttribute(Constants.Param.username);

		if (userId != null && username != null) {
	        return ResponseEntity.ok(Map.of("loggedIn", true, Constants.Param.userId, userId, Constants.Param.username , username));
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Map.of("loggedIn", false, "message", "No active session"));
	    }
	}
}
