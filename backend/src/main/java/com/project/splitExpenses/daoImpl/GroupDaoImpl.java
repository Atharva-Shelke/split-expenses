package com.project.splitExpenses.daoImpl;

import com.project.splitExpenses.dao.GroupDao;
import com.project.splitExpenses.dto.GroupDTO;
import com.project.splitExpenses.mappers.*;
import com.project.splitExpenses.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

//import oracle.jdbc.OracleTypes;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Repository
public class GroupDaoImpl implements GroupDao {
	private static final Logger logger = LoggerFactory.getLogger(GroupDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> searchGroups(GroupDTO group) {
		try {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_GROUP_SEARCH)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_NAME, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_START, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_LIMIT, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_RESULT, Types.REF_CURSOR, new GroupRowMapper()),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call state={}, group={}{}", group.getOperationState(), group);

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USER_ID, group.getUserId())
				.addValue(Constants.SPargument.I_GROUP_ID, group.getGroupId())
				.addValue(Constants.SPargument.I_GROUP_NAME, group.getGroupName())
				.addValue(Constants.SPargument.I_OPERATION_STATE, group.getOperationState())
				.addValue(Constants.SPargument.I_START,Constants.Value.START)
				.addValue(Constants.SPargument.I_LIMIT,Constants.Value.LIMIT);

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call group={}", group);
		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		output.put(Constants.Param.groups, result.get(Constants.SPargument.O_RESULT));
		return output;
		}
		catch (Exception ex) {
			logger.error("Exception occured in searchGroup: [{}]",ex);
			Map<String, Object> error = new HashMap<>();
			error.put(Constants.Param.statusCode, 999);
			error.put(Constants.Param.message, ex);
			return error;
		}
	}

	@Override
	public Map<String, Object> manageGroup(GroupDTO group) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_GROUP_MANAGE)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_NAME, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call state={}, param={}", group.getOperationState(), group);

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USER_ID, group.getUserId())
				.addValue(Constants.SPargument.I_GROUP_ID, group.getGroupId())
				.addValue(Constants.SPargument.I_GROUP_NAME, group.getGroupName())
				.addValue(Constants.SPargument.I_OPERATION_STATE, group.getOperationState());

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call param={}", group);

		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		return output;
	}
	
	@Override
	public Map<String, Object> searchGroupMembers(GroupDTO group) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_GROUP_MEMBER_SEARCH)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_MEMBER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_START, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_LIMIT, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_RESULT, Types.REF_CURSOR, new GroupRowMapper()),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call state={}, group={}", group.getOperationState(), group);

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USER_ID, group.getUserId())
				.addValue(Constants.SPargument.I_GROUP_ID, group.getGroupId())
				.addValue(Constants.SPargument.I_MEMBER_ID, group.getMemberId())
				.addValue(Constants.SPargument.I_OPERATION_STATE, group.getOperationState())
				.addValue(Constants.SPargument.I_START,Constants.Value.START)
				.addValue(Constants.SPargument.I_LIMIT,Constants.Value.LIMIT);

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call group={}", group);
		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		output.put(Constants.Param.members, result.get(Constants.SPargument.O_RESULT));
		return output;
	}

	@Override
	public Map<String, Object> manageGroupMember(GroupDTO group) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_GROUP_MEMBER_MANAGE)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_MEMBER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call state={}, memberId={}", group.getOperationState(), group.getMemberId());

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USER_ID, group.getUserId())
				.addValue(Constants.SPargument.I_GROUP_ID, group.getGroupId())
				.addValue(Constants.SPargument.I_MEMBER_ID, group.getMemberId())
				.addValue(Constants.SPargument.I_OPERATION_STATE, group.getOperationState());

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call param={}", group);

		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		return output;
	}
}
