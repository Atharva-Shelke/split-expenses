package com.project.splitExpenses.service;

import com.project.splitExpenses.dao.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

import com.project.splitExpenses.dto.ExpenseDTO;
import com.project.splitExpenses.dto.GroupDTO;
import com.project.splitExpenses.dto.SettlementDTO;
import com.project.splitExpenses.dto.SplitDTO;
import com.project.splitExpenses.dto.UserDTO;
import com.project.splitExpenses.util.Constants;

@Service
public class ServiceImpl implements SEservice {

	private static final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

	private final UserDao userDao;
	private final GroupDao groupDao;
	private final SplitExpenseDao splitExpenseDao;

    public ServiceImpl(UserDao userDao, GroupDao groupDao, SplitExpenseDao splitExpenseDao) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.splitExpenseDao = splitExpenseDao;
    }

	@Override
	public Map<String, Object> getUsers() {
		UserDTO user = new UserDTO();
		user.setOperationState(Constants.OPstate.FETCH_ALL);
		return userDao.searchUsers(user);
	}

	@Override
	public Map<String, Object> getUserByParam(UserDTO userRequest) {
		userRequest.setOperationState(Constants.OPstate.FETCH_BY_PARAM);
		logger.debug("getUser : {}",userRequest);
		return userDao.searchUsers(userRequest);
	}
	
	@Override
	public Map<String, Object> createUser(UserDTO userRequest) {
		userRequest.setOperationState(Constants.OPstate.CREATE);
		return userDao.manageUser(userRequest);
	}

	@Override
	public Map<String, Object> updatePassword(UserDTO userRequest) {
		userRequest.setOperationState(Constants.OPstate.UPDATE_PASSWORD);
		return userDao.manageUser(userRequest);
	}

	@Override
	public Map<String, Object> updateUser(UserDTO userRequest) {
		userRequest.setOperationState(Constants.OPstate.UPDATE);
		return userDao.manageUser(userRequest);
	}

	@Override
	public Map<String, Object> deleteUser(int userId) {
		UserDTO user = new UserDTO();
		user.setOperationState(Constants.OPstate.DELETE);
		user.setUserId(userId);
		return userDao.manageUser(user);
	}

	@Override
	public Map<String, Object> getGroups(long userId) {
		GroupDTO groups = new GroupDTO();
		groups.setUserId(userId);
		groups.setOperationState(Constants.OPstate.FETCH_ALL);
		return groupDao.searchGroups(groups);
	}

	@Override
	public Map<String, Object> getGroupByParam(GroupDTO groupRequest) {
		groupRequest.setOperationState(Constants.OPstate.FETCH_BY_PARAM);
		logger.debug("getGroup : {}",groupRequest.getOperationState());
		return groupDao.searchGroups(groupRequest);
	}

	@Override
	public Map<String, Object> createGroup(GroupDTO groupRequest) {
		groupRequest.setOperationState(Constants.OPstate.CREATE);
		return groupDao.manageGroup(groupRequest);
	}

	@Override
	public Map<String, Object> updateGroup(GroupDTO groupRequest) {
		groupRequest.setOperationState(Constants.OPstate.UPDATE);
		return groupDao.manageGroup(groupRequest);
	}

	@Override
	public Map<String, Object> deleteGroup(long groupId, long userId) {
		GroupDTO groupRequest = new GroupDTO();
		groupRequest.setOperationState(Constants.OPstate.DELETE);
		groupRequest.setUserId(userId);
		groupRequest.setGroupId(groupId);
		return groupDao.manageGroup(groupRequest);
	}

	@Override
	public Map<String, Object> getGroupMembers(long userId, long groupId) {
		GroupDTO groupMembers = new GroupDTO();
		groupMembers.setUserId(userId);
		groupMembers.setGroupId(groupId);
		groupMembers.setOperationState(Constants.OPstate.FETCH_ALL);
		return groupDao.searchGroupMembers(groupMembers);
	}

	@Override
	public Map<String, Object> getGroupMemberByParam(GroupDTO groupRequest) {
		groupRequest.setOperationState(Constants.OPstate.FETCH_ALL);
		logger.debug("getGroup : {}",groupRequest.getOperationState());
		return groupDao.searchGroupMembers(groupRequest);
	}

	@Override
	public Map<String, Object> createGroupMember(GroupDTO groupRequest) {
		groupRequest.setOperationState(Constants.OPstate.CREATE);
		return groupDao.manageGroupMember(groupRequest);
	}

	@Override
	public Map<String, Object> updateGroupMember(GroupDTO groupRequest) {
		groupRequest.setOperationState(Constants.OPstate.UPDATE);
		return groupDao.manageGroupMember(groupRequest);
	}

	@Override
	public Map<String, Object> deleteGroupMember(long userId, long groupId, long memberId) {
		GroupDTO groupMember = new GroupDTO();
		groupMember.setOperationState(Constants.OPstate.DELETE);
		groupMember.setUserId(userId);
		groupMember.setGroupId(groupId);
		groupMember.setMemberId(memberId);
		return groupDao.manageGroupMember(groupMember);
	}

	@Override
	public Map<String, Object> getExpense(long userId, long groupId, long memberId, int toggleView) {
		ExpenseDTO expenses = new ExpenseDTO();
		expenses.setUserId(userId);
		expenses.setGroupId(groupId);
		expenses.setMemberId(memberId);
		expenses.setToggleView(toggleView);
		expenses.setOperationState(Constants.OPstate.FETCH_ALL);
		return splitExpenseDao.searchExpense(expenses);
	}

	@Override
	public Map<String, Object> getExpenseByParam(ExpenseDTO expenseRequest) {
		expenseRequest.setOperationState(Constants.OPstate.FETCH_BY_PARAM);
		logger.debug("getExpense : {}",expenseRequest);
		return splitExpenseDao.searchExpense(expenseRequest);
	}

	@Override
	public Map<String, Object> createExpense(ExpenseDTO expenseRequest) {
		expenseRequest.setOperationState(Constants.OPstate.CREATE);
		return splitExpenseDao.manageExpense(expenseRequest);
	}

	@Override
	public Map<String, Object> updateExpense(ExpenseDTO expenseRequest) {
		expenseRequest.setOperationState(Constants.OPstate.UPDATE);
		return splitExpenseDao.manageExpense(expenseRequest);
	}

	@Override
	public Map<String, Object> deleteExpense(long userId, long expenseId) {
		ExpenseDTO expenseRequest = new ExpenseDTO();
		expenseRequest.setUserId(userId);
		expenseRequest.setExpenseId(expenseId);
		expenseRequest.setOperationState(Constants.OPstate.DELETE);
		return splitExpenseDao.manageExpense(expenseRequest);
	}

	@Override
	public Map<String, Object> getSplit(long userId, long groupId, long memberId) {
		SplitDTO split = new SplitDTO();
		split.setUserId(userId);
		split.setGroupId(groupId);
		split.setMemberId(memberId);
		split.setOperationState("SPLIT_REQUEST");
		return splitExpenseDao.searchSplit(split);
	}

	@Override
	public Map<String, Object> getSettlement(long userId, long groupId, int toggleView) {
		SettlementDTO settlements = new SettlementDTO();
		settlements.setUserId(userId);
		settlements.setGroupId(groupId);
		settlements.setToggleView(toggleView);
		settlements.setOperationState(Constants.OPstate.FETCH_ALL);
		return splitExpenseDao.searchSettlement(settlements);
	}

	@Override
	public Map<String, Object> getSettlementByParam(SettlementDTO settlementRequest) {
		settlementRequest.setOperationState(Constants.OPstate.FETCH_BY_PARAM);
		logger.debug("getSettlement : {}",settlementRequest);
		return splitExpenseDao.searchSettlement(settlementRequest);
	}

	@Override
	public Map<String, Object> createSettlement(SettlementDTO settlementRequest) {
		settlementRequest.setRequestId(0);
		settlementRequest.setOperationState(Constants.OPstate.CREATE);
		return splitExpenseDao.manageSettlement(settlementRequest);
	}

	@Override
	public Map<String, Object> approveSettlement(SettlementDTO settlementRequest) {
		settlementRequest.setOperationState(Constants.OPstate.APPROVE);
		return splitExpenseDao.manageSettlement(settlementRequest);
	}
	
	@Override
	public Map<String, Object> rejectSettlement(SettlementDTO settlementRequest) {
		settlementRequest.setOperationState(Constants.OPstate.REJECT);
		return splitExpenseDao.manageSettlement(settlementRequest);
	}

	@Override
	public Map<String, Object> deleteSettlement(long settlementId) {
		SettlementDTO settlementRequest = new SettlementDTO();
		return splitExpenseDao.manageSettlement(settlementRequest);
	}
}
