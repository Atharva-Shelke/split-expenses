package com.project.splitExpenses.daoImpl;

import com.project.splitExpenses.dao.UserDao;
import com.project.splitExpenses.dto.UserDTO;
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
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> login(String username, String password) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_USER_LOGIN)
				.withoutProcedureColumnMetaDataAccess()
				.declareParameters(
						new SqlParameter(Constants.SPargument.I_USERNAME, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_PASSWORD, Types.VARCHAR),
						new SqlOutParameter(Constants.SPargument.O_RESULT, Types.REF_CURSOR, new UserRowMapper()),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Dao {}:{} debug successfully", username, password);

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USERNAME, username)
				.addValue(Constants.SPargument.I_PASSWORD, password);

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("rsult>>{}",result);
		logger.debug("rsult>>{}",result.get("O_RESULT"));

		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		@SuppressWarnings("unchecked")
		List<UserDTO> users = (List<UserDTO>) result.get(Constants.SPargument.O_RESULT);
	    UserDTO user = (users != null && !users.isEmpty()) ? users.get(0) : null;

	    output.put("currentUser", user);
		return output;
	}

	@Override
	public Map<String, Object> searchUsers(UserDTO user) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_USER_SEARCH)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_USERNAME, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_EMAIL, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_START, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_LIMIT, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_RESULT, Types.REF_CURSOR, new UserRowMapper()),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call state={}, user={}{}", user.getOperationState(), user.getUsername(),
				user.getEmail());

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USER_ID, user.getUserId())
				.addValue(Constants.SPargument.I_USERNAME, user.getUsername())
				.addValue(Constants.SPargument.I_EMAIL, user.getEmail())
				.addValue(Constants.SPargument.I_START, Constants.Value.START)
				.addValue(Constants.SPargument.I_LIMIT, Constants.Value.LIMIT)
				.addValue(Constants.SPargument.I_OPERATION_STATE, user.getOperationState());

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call user={}", user);
		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		output.put(Constants.Param.users, result.get(Constants.SPargument.O_RESULT));
		return output;
	}

	@Override
	public Map<String, Object> manageUser(UserDTO user) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_USER_MANAGE)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_USERNAME, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_EMAIL, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_MOBILE_NO, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_PASSWORD, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call operationState={}, param={}", user.getOperationState(), user);

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USER_ID, user.getUserId())
				.addValue(Constants.SPargument.I_USERNAME, user.getUsername())
				.addValue(Constants.SPargument.I_EMAIL, user.getEmail())
				.addValue(Constants.SPargument.I_MOBILE_NO, user.getMobileNo())
				.addValue(Constants.SPargument.I_PASSWORD, user.getPassword())
				.addValue(Constants.SPargument.I_OPERATION_STATE, user.getOperationState());

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call param={}", user);

		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		return output;
	}
}
