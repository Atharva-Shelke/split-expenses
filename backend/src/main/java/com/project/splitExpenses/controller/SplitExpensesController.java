package com.project.splitExpenses.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.*;

import com.project.splitExpenses.dto.ExpenseDTO;
import com.project.splitExpenses.dto.GroupDTO;
import com.project.splitExpenses.dto.SettlementDTO;
import com.project.splitExpenses.dto.UserDTO;
import com.project.splitExpenses.service.SEservice;


@RestController
@RequestMapping("/api")

public class SplitExpensesController {

	private static final Logger logger = LoggerFactory.getLogger(SplitExpensesController.class);

	@Autowired
	private SEservice seService;

	@GetMapping("/users/search")
	public Map<String, Object> getUsers() {
		return seService.getUsers();
	} 

	@PostMapping("/users/searchByParam")
	public Map<String, Object> getUserByParam(@RequestBody UserDTO userRequest) {
		logger.debug("getUserByParam()[ param= {}] ", userRequest);
		return seService.getUserByParam(userRequest);
	}
	
	@PostMapping("/users/create")
	public Map<String, Object> createUser(@RequestBody UserDTO userRequest) {
		logger.debug("createUser()[ param= {}] ", userRequest.getUsername());
		return seService.createUser(userRequest);
	}

	@PutMapping("/users/updatePassword")
	public Map<String, Object> updatePassword(@RequestBody UserDTO userRequest) {
		logger.debug("updatePassword()[ param= {}] ", userRequest);
		return seService.updatePassword(userRequest);
	}

	@PutMapping("/users/update")
	public Map<String, Object> updateUser(@RequestBody UserDTO userRequest) {
		logger.debug("updateUser()[ param= {}] ", userRequest);
		return seService.updateUser(userRequest);
	}

	@DeleteMapping("/users/delete/{userId}")
	public Map<String, Object> deleteUser(@PathVariable int userId) {
		logger.debug("deleteUser()[ userId= {}] ", userId);
		return seService.deleteUser(userId);
	}

	@GetMapping("/groups/search")
	public Map<String, Object> getGroups(@RequestParam long userId ) {
		logger.debug("getGroups by userId:{}",userId);
		return seService.getGroups(userId);
	}

	@PostMapping("/groups/searchByParam")
	public Map<String, Object> getGroupByParam(@RequestBody GroupDTO groupRequest) {
		logger.debug("getGroupByParam()[ param= {}] ", groupRequest);
		return seService.getGroupByParam(groupRequest);
	}

	@PostMapping("/groups/create")
	public Map<String, Object> createGroup(@RequestBody GroupDTO groupRequest) {
		logger.debug("createGroup()[ param= {}] ", groupRequest);
		return seService.createGroup(groupRequest);
	}

	@PutMapping("/groups/update")
	public Map<String, Object> updateGroup(@RequestBody GroupDTO groupRequest) {
		logger.debug("updateGroup()[ param= {}] ", groupRequest);
		return seService.updateGroup(groupRequest);
	}

	@DeleteMapping("/groups/delete")
	public Map<String, Object> deleteGroup(@RequestParam long groupId, @RequestParam long userId) {
		logger.debug("deleteGroup()[ groupId= {} by userId= {}] ", groupId,userId);
		return seService.deleteGroup(groupId, userId);
	}

	@GetMapping("/groupMembers/search")
	public Map<String, Object> getGroupMembers(@RequestParam long userId, @RequestParam String groupId) {
		Long gId = Long.parseLong(groupId);
		logger.debug("getGroupMembers by userId:{}, groupId:{}", userId, gId);
		return seService.getGroupMembers(userId, gId);
	}

	@PostMapping("/groupMembers/searchByParam")
	public Map<String, Object> getGroupMemberByParam(@RequestBody GroupDTO groupRequest) {
		logger.debug("getGroupMemberByParam()[ param= {}] ", groupRequest);
		return seService.getGroupMemberByParam(groupRequest);
	}

	@PostMapping("/groupMembers/create")
	public Map<String, Object> createGroupMember(@RequestBody GroupDTO groupRequest) {
		logger.debug("createGroupMember()[ param= {}] ", groupRequest);
		return seService.createGroupMember(groupRequest);
	}

	@PutMapping("/groupMembers/update")
	public Map<String, Object> updateGroupMember(@RequestBody GroupDTO groupRequest) {
		logger.debug("updateGroupMember()[ param= {}] ", groupRequest);
		return seService.updateGroupMember(groupRequest);
	}

	@DeleteMapping("/groupMembers/delete")
	public Map<String, Object> deleteGroupMember(@RequestParam long memberId, @RequestParam long groupId, @RequestParam long userId) {
		logger.debug("deleteGroupMember()[ userId= {}, groupId= {}, memberId= {}] ", userId, groupId, memberId);
		return seService.deleteGroupMember(userId, groupId, memberId);
	}
	
	@GetMapping("/expense/search")
	public Map<String, Object> getExpense(@RequestParam long userId, @RequestParam long groupId, @RequestParam long memberId, @RequestParam int toggleView) {
		logger.debug("getExpense()[ userId= {}, groupId= {}, memberId= {}, toggleView= {}] ", userId, groupId, memberId, toggleView);
		return seService.getExpense(userId, groupId, memberId, toggleView);
	}

	@PostMapping("/expense/searchByParam")
	public Map<String, Object> getExpenseByParam(@RequestBody ExpenseDTO expenseRequest) {
		logger.debug("getExpenseByParam()[ param= {}] ", expenseRequest);
		return seService.getExpenseByParam(expenseRequest);
	}

	@PostMapping("/expense/create")
	public Map<String, Object> createExpense(@RequestBody ExpenseDTO expenseRequest) {
		logger.debug("createExpense()[ param= {}] ", expenseRequest);
		logger.debug("createExpenseUserId()[ param= {}] ", expenseRequest.getUserId());
		return seService.createExpense(expenseRequest);
	}

	@PutMapping("/expense/update")
	public Map<String, Object> updateExpense(@RequestBody ExpenseDTO expenseRequest) {
		logger.debug("updateExpense()[ param= {}] ", expenseRequest);
		return seService.updateExpense(expenseRequest);
	}

	@DeleteMapping("/expense/delete")
	public Map<String, Object> deleteExpense(@RequestParam long expenseId, @RequestParam long userId) {
		logger.debug("deleteExpense()[ userId = {}, expenseId= {}] ", userId, expenseId);
		return seService.deleteExpense(userId, expenseId);
	}
	
	@GetMapping("/expenseSplit/search")
	public Map<String, Object> getSplit(@RequestParam long userId, @RequestParam long groupId, @RequestParam long memberId) {
		logger.debug("getSplit()[ userId= {}, groupId= {}, memberId= {}, toggleView= {}] ", userId, groupId, memberId);
		return seService.getSplit(userId, groupId, memberId);
	}
	
	@GetMapping("/settlement/search")
	public Map<String, Object> getSettlement(@RequestParam long userId, @RequestParam long groupId, @RequestParam int toggleView) {
		logger.debug("getSettlement()[userId = {}, groupId = {}, toggleView = {}]", userId, groupId, toggleView);
		return seService.getSettlement(userId, groupId, toggleView);
	}

	@PostMapping("/settlement/searchByParam")
	public Map<String, Object> getSettlementByParam(@RequestBody SettlementDTO settlementRequest) {
		logger.debug("getSettlementByParam()[ param= {}] ", settlementRequest);
		return seService.getSettlementByParam(settlementRequest);
	}

	@PostMapping("/settlement/create")
	public Map<String, Object> createSettlement(@RequestBody SettlementDTO settlementRequest) {
		logger.debug("createExpense()[ param= {}] ", settlementRequest);
		return seService.createSettlement(settlementRequest);
	}

	@PutMapping("/settlement/approve")
	public Map<String, Object> approveSettlement(@RequestBody SettlementDTO settlementRequest) {
		logger.debug("approveSettlement()[ param= {}] ", settlementRequest);
		return seService.approveSettlement(settlementRequest);
	}
	
	@PutMapping("/settlement/reject")
	public Map<String, Object> rejectSettlement(@RequestBody SettlementDTO settlementRequest) {
		logger.debug("rejectSettlement()[ param= {}] ", settlementRequest);
		return seService.rejectSettlement(settlementRequest);
	}

	@DeleteMapping("/settlement/delete/{memberId}")
	public Map<String, Object> deleteSettlement(@PathVariable long memberId) {
		logger.debug("deleteSettlement()[ userId= {}] ", memberId);
		return seService.deleteSettlement(memberId);
	}
}
