package com.project.splitExpenses.service;

import java.util.Map;

import com.project.splitExpenses.dto.ExpenseDTO;
import com.project.splitExpenses.dto.GroupDTO;
import com.project.splitExpenses.dto.SettlementDTO;

//import org.springframework.web.bind.annotation.RequestBody;

import com.project.splitExpenses.dto.UserDTO;

public interface SEservice {

	Map<String, Object> getUsers();

	Map<String, Object> getUserByParam(UserDTO userRequest);

	Map<String, Object> createUser(UserDTO userRequest);
	
	Map<String, Object> updatePassword(UserDTO userRequest);

	Map<String, Object> updateUser(UserDTO userRequest);

	Map<String, Object> deleteUser(int userId);

	Map<String, Object> getGroups(long userId);

	Map<String, Object> getGroupByParam(GroupDTO groupRequest);

	Map<String, Object> createGroup(GroupDTO grouprRequest);

	Map<String, Object> updateGroup(GroupDTO groupRequest);

	Map<String, Object> deleteGroup(long groupId, long userId);
	
	Map<String, Object> getGroupMembers(long userId, long groupId);

	Map<String, Object> getGroupMemberByParam(GroupDTO groupRequest);

	Map<String, Object> createGroupMember(GroupDTO grouprRequest);

	Map<String, Object> updateGroupMember(GroupDTO groupRequest);

	Map<String, Object> deleteGroupMember(long userId, long groupId, long memberId);
	
	Map<String, Object> getExpense(long userId, long groupId, long memberId, int toggleView);

	Map<String, Object> getExpenseByParam(ExpenseDTO expenseRequest);

	Map<String, Object> createExpense(ExpenseDTO expenseRequest);

	Map<String, Object> updateExpense(ExpenseDTO expenseRequest);

	Map<String, Object> deleteExpense(long userId, long expenseId);
	
	Map<String, Object> getSplit(long userId, long groupId, long memberId);
	
	Map<String, Object> getSettlement(long userId, long groupId, int toggleView);

	Map<String, Object> getSettlementByParam(SettlementDTO settlementRequest);

	Map<String, Object> createSettlement(SettlementDTO settlementRequest);
	
	Map<String, Object> approveSettlement(SettlementDTO settlementRequest);

	Map<String, Object> rejectSettlement(SettlementDTO settlementRequest);

	Map<String, Object> deleteSettlement(long settlementId);
}
