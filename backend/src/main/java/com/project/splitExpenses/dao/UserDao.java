package com.project.splitExpenses.dao;

import java.util.Map;

import com.project.splitExpenses.dto.UserDTO;

public interface UserDao {
	Map<String, Object> login(String usernameOrEmail, String password);

	Map<String, Object> searchUsers(UserDTO user);

	Map<String, Object> manageUser(UserDTO user);

}
