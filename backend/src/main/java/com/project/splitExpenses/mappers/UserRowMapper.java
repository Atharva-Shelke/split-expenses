package com.project.splitExpenses.mappers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.splitExpenses.dto.UserDTO;

public class UserRowMapper implements RowMapper<UserDTO> {

	@Override
	public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserDTO u = new UserDTO();
		u.setUserId(rs.getLong("USER_ID"));
		u.setUsername(rs.getString("USERNAME"));
		u.setEmail(getSafeString(rs, "EMAIL"));
		u.setMobileNo(getSafeString(rs, "MOBILE_NO"));
		return u;
	}

	private String getSafeString(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int columnCount = meta.getColumnCount();

		for (int i = 1; i <= columnCount; i++) {
			if (meta.getColumnLabel(i).equalsIgnoreCase(columnName)) {
				return rs.getString(columnName);
			}
		}
		return null;
	}

}
