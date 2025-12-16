package com.project.splitExpenses.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.splitExpenses.dto.UserDTO;
import com.project.splitExpenses.service.AuthService;
import com.project.splitExpenses.util.Constants;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request, HttpSession session) {
	    String username = request.get(Constants.Param.username);
	    String password = request.get(Constants.Param.password);
	    logger.debug("login user:{} password:{}", username, password);

	    Map<String, Object> result = authService.login(username, password);
	    logger.debug("result for login>>>{}",result);
	    logger.debug("result for login>>>{}",result.get("currentUser"));

	    Integer statusCode = (Integer) result.get(Constants.Param.statusCode);
	    logger.debug("login statusCode:{}",statusCode);

	    if (statusCode != null && statusCode == Constants.Value.SUCCESS_CODE) { // 100 = success
	    	UserDTO user = (UserDTO) result.get("currentUser");
	    	session.setAttribute(Constants.Param.userId, user.getUserId());
	    	session.setAttribute(Constants.Param.username, user.getUsername());
	        result.put(Constants.Param.message, result.get(Constants.Param.message));
	        return ResponseEntity.ok(result);
	    } else {
	    	result.put(Constants.Param.message, result.get(Constants.Param.message));
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	    }
	}

	@GetMapping("/session")
	public ResponseEntity<Map<String, Object>> checkSession(HttpSession session) {
		logger.debug("session=[ userId={}, username={}]",session.getAttribute(Constants.Param.userId), session.getAttribute(Constants.Param.username));
	    return authService.checkSession(session);

	}

	@PostMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
	    Map<String, Object> result = new HashMap<>();

	    try {
	        session.invalidate();
	        result.put(Constants.Param.message, "Logged out successfully");
	        result.put("loggedIn", false);
	        return ResponseEntity.ok(result);
	    } catch (IllegalStateException e) {
	        result.put(Constants.Param.message, "No active session");
	        result.put("loggedIn", false);
	        return ResponseEntity.ok(result);
	    }
	}
}
