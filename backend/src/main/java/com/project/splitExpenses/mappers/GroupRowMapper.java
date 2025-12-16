package com.project.splitExpenses.mappers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.splitExpenses.dto.GroupDTO;
import com.project.splitExpenses.util.Constants;

public class GroupRowMapper implements RowMapper<GroupDTO> {
	@Override
	public GroupDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		GroupDTO group = new GroupDTO();
		group.setGroupId(rs.getLong("GROUP_ID"));
		group.setGroupName(getSafeString(rs,"GROUP_NAME"));
		group.setCreatedBy(getSafeString(rs,"CREATED_BY"));
		group.setCreatedOn(getSafeString(rs,"CREATED_ON"));
		group.setMemberId(getSafeLong(rs,"MEMBER_ID"));
		group.setAddedOn(getSafeString(rs,"ADDED_ON"));
		group.setMemberName(getSafeString(rs,"MEMBER_NAME"));
		group.setMemberType(getSafeString(rs,"MEMBER_TYPE"));
		return group;
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
