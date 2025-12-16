package com.project.splitExpenses.mappers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.project.splitExpenses.dto.SplitDTO;

public class SplitRowMapper implements RowMapper<SplitDTO> {

	@Override
	public SplitDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		SplitDTO s = new SplitDTO();
		s.setTotalSplitAmount(rs.getDouble("TOTAL_SPLIT_AMOUNT"));
		s.setCanRequest(rs.getInt("CAN_REQUEST"));
		s.setExistingRequestStatus(rs.getInt("EXISTING_REQUEST_STATUS"));
		return s;
	}

}
