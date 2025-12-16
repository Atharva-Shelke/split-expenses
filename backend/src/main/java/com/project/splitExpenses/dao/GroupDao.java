package com.project.splitExpenses.dao;

import java.util.Map;

import com.project.splitExpenses.dto.GroupDTO;

public interface GroupDao {

	Map<String, Object> searchGroups(GroupDTO group);

	Map<String, Object> manageGroup(GroupDTO group);

	Map<String, Object> searchGroupMembers(GroupDTO group);

	Map<String, Object> manageGroupMember(GroupDTO group);

}
