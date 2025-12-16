package com.project.splitExpenses.dto;

import java.sql.Timestamp;

public class Request {
	private long userId;
	private long groupId;
	private String username;
	private String operationState;
	private int start;
	private int limit;
	private String createdBy;
	private long createdById;
	private String createdOn;
	private long modifiedById;
	private String modifiedOn;
	private long memberId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperationState() {
		return operationState;
	}

	public void setOperationState(String operationState) {
		this.operationState = operationState;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(long createdById) {
		this.createdById = createdById;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public long getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(long modifiedById) {
		this.modifiedById = modifiedById;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "RequestDTO [userId=" + userId + ", groupId=" + groupId + ", username=" + username + ", operationState=" + operationState + ", start=" + start + ", limit="
				+ limit + ", createdBy=" + createdBy + ", createdById=" + createdById + ", createdOn=" + createdOn
				+ ", modifiedById=" + modifiedById + ", modifiedOn=" + modifiedOn + ", memberId=" + memberId + "]";
	}

//	public static void main(String[] args) {
//		Request request = new Request();
//		System.out.println(request);
//	}
}
