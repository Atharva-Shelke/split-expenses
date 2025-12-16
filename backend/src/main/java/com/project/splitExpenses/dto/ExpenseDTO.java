package com.project.splitExpenses.dto;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDTO extends Request{
	private Long expenseId;
//	private Long groupId;
	private Long userId; // who created this expense
	private String description;
	private Double amount;
	private long paidBy;
	private ArrayList<Integer> memberIds;
	private String memberIdsS;
	private String memberNames;
	private int toggleView;
	private double splitAmount;

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

//	public Long getGroupId() {
//		return groupId;
//	}
//
//	public void setGroupId(Long groupId) {
//		this.groupId = groupId;
//	}

//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

public long getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(long paidBy) {
		this.paidBy = paidBy;
	}

	//	public Time getCreatedOn() {
//		return createdOn;
//	}
//
//	public void setCreatedOn(Time createdOn) {
//		this.createdOn = createdOn;
//	}
	
	public ArrayList<Integer> getMemberIds() {
		return memberIds;
	}

//	public void setMemberIdsS(String memberIdsS) {
//		this.memberIdsS = memberIdsS;
//	}
	public String getMemberIdsS() {
		return memberIdsS;
	}

	public void setMemberIdsS(String memberIdsS) {
		this.memberIdsS = memberIdsS;
	}
	
	public String getMemberNames() {
		return memberNames;
	}

	public void setMemberNames(String memberNames) {
		this.memberNames = memberNames;
	}

	public int getToggleView() {
		return toggleView;
	}

	public void setToggleView(int toggleView) {
		this.toggleView = toggleView;
	}

	public double getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(double splitAmount) {
		this.splitAmount = splitAmount;
	}

//	@Override
//	public String toString() {
//		return "ExpenseDTO [expenseId=" + expenseId + ", groupId=" + groupId + ", userId=" + userId + ", description=" + description
//				+ ", amount=" + amount + ", memberIdsS=" + memberIdsS + ", toggleView=" + toggleView + ", splitAmount=" + splitAmount + "]";
//	}

}
