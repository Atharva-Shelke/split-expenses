package com.project.splitExpenses.dto;

import java.util.List;

public class GroupDTO extends Request {
	private String groupName;
	private String groupMemberName;
	private List<Long> memberIds;
	private String addedOn;
	private String memberType;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMemberName() {
		return groupMemberName;
	}

	public void setMemberName(String groupMemberName) {
		this.groupMemberName = groupMemberName;
	}

	public List<Long> getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}

	public String getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(String addedOn) {
		this.addedOn = addedOn;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	@Override
	public String toString() {
		return "GroupDTO [groupName=" + groupName + ", groupMemberName=" + groupMemberName + ", memberIds=" + memberIds
				+ ", addedOn=" + addedOn + ", memberType=" + memberType + "]";
	}

}
