package com.project.splitExpenses.mappers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.project.splitExpenses.dto.ExpenseDTO;
import com.project.splitExpenses.util.Constants;

public class ExpenseRowMapper implements RowMapper<ExpenseDTO> {

	@Override
	public ExpenseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExpenseDTO e = new ExpenseDTO();
		e.setExpenseId(getSafeLong(rs, "EXPENSE_ID"));
		e.setGroupId(getSafeLong(rs, "GROUP_ID"));
		e.setPaidBy(getSafeLong(rs, "PAID_BY"));
		e.setCreatedBy(getSafeString(rs, "CREATED_BY"));
		e.setDescription(getSafeString(rs, "DESCRIPTION"));
		e.setAmount(rs.getDouble("AMOUNT"));
		e.setCreatedOn(getSafeString(rs, "CREATED_ON"));
		e.setMemberNames(getSafeString(rs, "MEMBER_NAMES"));
		e.setMemberIdsS(getSafeString(rs, "MEMBER_IDS"));
		e.setSplitAmount(rs.getDouble("SPLIT_AMOUNT"));
		return e;
	}

	private String getSafeString(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int columnCount = meta.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			if (meta.getColumnLabel(i).equalsIgnoreCase(columnName)) {
				return rs.getString(columnName);
			}
		}
		return Constants.Value.EMPTY;
	}

	private Long getSafeLong(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int columnCount = meta.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			if (meta.getColumnLabel(i).equalsIgnoreCase(columnName)) {
				return rs.getLong(columnName);
			}
		}
		return 0l;
	}
}
