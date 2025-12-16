package com.project.splitExpenses.util;

public final class Constants {
	private Constants() {
		// private constructor to make it constant
	}

	public static final class StoredProcedure {
		public static final String PRC_USER_LOGIN = "PRC_USER_LOGIN";
		public static final String PRC_USER_SEARCH = "PRC_USER_SEARCH";
		public static final String PRC_USER_MANAGE = "PRC_USER_MANAGE";
		public static final String PRC_GROUP_SEARCH = "PRC_GROUP_SEARCH";
		public static final String PRC_GROUP_MANAGE = "PRC_GROUP_MANAGE";
		public static final String PRC_GROUP_MEMBER_SEARCH = "PRC_GROUP_MEMBER_SEARCH";
		public static final String PRC_GROUP_MEMBER_MANAGE = "PRC_GROUP_MEMBER_MANAGE";
		public static final String PRC_EXPENSE_SEARCH = "PRC_EXPENSE_SEARCH";
		public static final String PRC_EXPENSE_MANAGE = "PRC_EXPENSE_MANAGE";
		public static final String PRC_SETTLEMENT_SEARCH = "PRC_SETTLEMENT_SEARCH";
		public static final String PRC_SETTLEMENT_MANAGE = "PRC_SETTLEMENT_MANAGE";
	}

	public static final class SPargument {
		public static final String I_USERNAME = "I_USERNAME";
		public static final String I_PASSWORD = "I_PASSWORD";
		public static final String I_USER_ID = "I_USER_ID";
		public static final String I_OPERATION_STATE = "I_OPERATION_STATE";
		public static final String I_START = "I_START";
		public static final String I_LIMIT = "I_LIMIT";
		public static final String I_EMAIL = "I_EMAIL";
		public static final String I_MOBILE_NO = "I_MOBILE_NO";
		public static final String I_GROUP_ID = "I_GROUP_ID";
		public static final String I_GROUP_NAME = "I_GROUP_NAME";
		public static final String I_MEMBER_ID = "I_MEMBER_ID";
		public static final String I_DESCRIPTION = "I_DESCRIPTION";
		public static final String I_AMOUNT = "I_AMOUNT";
		public static final String I_MEMBER_IDS = "I_MEMBER_IDS";
		public static final String I_EXPENSE_ID = "I_EXPENSE_ID";
		public static final String I_REQUEST_ID = "I_REQUEST_ID";
		
		public static final String O_RESULT = "O_RESULT";
		public static final String O_STATUS_CODE = "O_STATUS_CODE";
		public static final String O_STATUS_MESSAGE = "O_STATUS_MESSAGE";
		public static final String I_TOGGLE_VIEW = "I_TOGGLE_VIEW";
	}

	public static final class Param {
		public static final String username = "username";
		public static final String password = "password";
		public static final String userId = "userId";
		public static final String statusCode = "statusCode";
		public static final String message = "message";
		public static final String users = "users";
		public static final String groups = "groups";
		public static final String members = "members";
	}
	
	public static final class OPstate {
		public static final String FETCH_ALL = "FETCH_ALL";
		public static final String FETCH_BY_PARAM = "FETCH_BY_PARAM";
		public static final String CREATE = "CREATE";
		public static final String UPDATE = "UPDATE";
		public static final String DELETE = "DELETE";
		public static final String UPDATE_PASSWORD = "UPDATE_PASSWORD";
		public static final String APPROVE = "APPROVE";
		public static final String REJECT = "REJECT";
	}
	
	public static final class Value {
		public static final int START = 0;
		public static final int LIMIT = 10;
		public static final int SUCCESS_CODE = 100;
		public static final int ZERO = 0;
		public static final String EMPTY = "";
	}
}
