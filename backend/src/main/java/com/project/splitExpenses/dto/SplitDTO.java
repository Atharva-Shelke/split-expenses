package com.project.splitExpenses.dto;

public class SplitDTO extends Request {

	private Double totalSplitAmount;
	private String statement;
	private Integer canRequest;
	private Integer requestExists;
	private Integer existingRequestStatus;

	public Double getTotalSplitAmount() {
		return totalSplitAmount;
	}

	public void setTotalSplitAmount(Double totalSplitAmount) {
		this.totalSplitAmount = totalSplitAmount;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Integer getCanRequest() {
		return canRequest;
	}

	public void setCanRequest(Integer canRequest) {
		this.canRequest = canRequest;
	}

	public Integer getRequestExists() {
		return requestExists;
	}

	public void setRequestExists(Integer requestExists) {
		this.requestExists = requestExists;
	}

	public Integer getExistingRequestStatus() {
		return existingRequestStatus;
	}

	public void setExistingRequestStatus(Integer existingRequestStatus) {
		this.existingRequestStatus = existingRequestStatus;
	}

	@Override
	public String toString() {
		return "SplitDTO [totalSplitAmount=" + totalSplitAmount + ", statement=" + statement + ", canRequest="
				+ canRequest + ", requestExists=" + requestExists + ", existingRequestStatus=" + existingRequestStatus
				+ "]";
	}

}
