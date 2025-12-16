package com.project.splitExpenses.dto;

public class SettlementDTO extends Request {
	private long requestId;
	private int toggleView;
	private long fromUser;
	private String fromUsername;
	private long toUser;
	private String toUsername;
	private int statusCode;
	private String statusDescription;
	private String remarks;
	private double amount;

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public int getToggleView() {
		return toggleView;
	}

	public void setToggleView(int toggleView) {
		this.toggleView = toggleView;
	}

	public long getFromUser() {
		return fromUser;
	}

	public void setFromUser(long fromUser) {
		this.fromUser = fromUser;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public long getToUser() {
		return toUser;
	}

	public void setToUser(long toUser) {
		this.toUser = toUser;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "SettlementDTO [requestId=" + requestId + ", toggleView=" + toggleView + ", fromUser=" + fromUser
				+ ", fromUsername=" + fromUsername + ", toUser=" + toUser + ", toUsername=" + toUsername
				+ ", statusCode=" + statusCode + ", statusDescription=" + statusDescription + ", remarks=" + remarks
				+ ", amount=" + amount + "]";
	}

}
