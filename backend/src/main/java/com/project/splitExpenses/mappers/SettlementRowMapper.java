package com.project.splitExpenses.mappers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.project.splitExpenses.dto.SettlementDTO;

public class SettlementRowMapper implements RowMapper<SettlementDTO> {

	@Override
	public SettlementDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SettlementDTO e = new SettlementDTO();
		e.setRequestId(rs.getLong("REQUEST_ID"));
		e.setGroupId(rs.getLong("GROUP_ID"));
		e.setFromUser(rs.getLong("FROM_USER"));
		e.setFromUsername(getSafeString(rs, "FROM_USERNAME"));
		e.setToUser(rs.getLong("TO_USER"));
		e.setToUsername(getSafeString(rs, "TO_USERNAME"));
		e.setAmount(rs.getDouble("AMOUNT"));
		e.setStatusCode(rs.getInt("STATUS_CODE"));
		e.setStatusDescription(getSafeString(rs, "STATUS_DESCRIPTION"));
		e.setCreatedOn(getSafeString(rs, "CREATED_ON"));
		e.setModifiedOn(getSafeString(rs, "MODIFIED_ON"));
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
		return "";
	}

}
