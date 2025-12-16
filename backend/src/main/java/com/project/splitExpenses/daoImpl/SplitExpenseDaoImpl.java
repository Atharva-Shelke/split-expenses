package com.project.splitExpenses.daoImpl;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.project.splitExpenses.dao.SplitExpenseDao;
import com.project.splitExpenses.dto.ExpenseDTO;
import com.project.splitExpenses.dto.SettlementDTO;
import com.project.splitExpenses.dto.SplitDTO;
import com.project.splitExpenses.mappers.ExpenseRowMapper;
import com.project.splitExpenses.mappers.SplitRowMapper;
import com.project.splitExpenses.mappers.SettlementRowMapper;
import com.project.splitExpenses.util.Constants;

@Repository
public class SplitExpenseDaoImpl implements SplitExpenseDao {
	private static final Logger logger = LoggerFactory.getLogger(SplitExpenseDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> searchExpense(ExpenseDTO expense) {
		try {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_EXPENSE_SEARCH)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_MEMBER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_TOGGLE_VIEW, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_START, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_LIMIT, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_RESULT, Types.REF_CURSOR, new ExpenseRowMapper()),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call state={}, expense={}", expense.getOperationState(),expense);

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USER_ID, expense.getUserId())
				.addValue(Constants.SPargument.I_GROUP_ID, expense.getGroupId())
				.addValue(Constants.SPargument.I_MEMBER_ID, expense.getMemberId())
				.addValue(Constants.SPargument.I_TOGGLE_VIEW, expense.getToggleView())
				.addValue(Constants.SPargument.I_OPERATION_STATE, expense.getOperationState())
				.addValue(Constants.SPargument.I_START, Constants.Value.START)
				.addValue(Constants.SPargument.I_LIMIT, Constants.Value.LIMIT);

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call expense={}", expense);
		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		output.put("expense", result.get(Constants.SPargument.O_RESULT));
		logger.debug("After procedure call output={}", output);
		return output;
		}
		catch(Exception ex) {
			logger.error("Exception in searchExpense: {}",ex);
			Map<String,Object> exc = new HashMap<>();
			exc.put(Constants.Param.statusCode, 999);
			exc.put(Constants.Param.message, ex.getMessage());
			return exc;
		}
	}

	@Override
	public Map<String, Object> manageExpense(ExpenseDTO expense) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_EXPENSE_MANAGE)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_DESCRIPTION, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_AMOUNT, Types.DOUBLE),
						new SqlParameter(Constants.SPargument.I_MEMBER_IDS, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_EXPENSE_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call state={}, param={}", expense.getOperationState(), expense);

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USER_ID, expense.getUserId())
				.addValue(Constants.SPargument.I_GROUP_ID, expense.getGroupId())
				.addValue(Constants.SPargument.I_DESCRIPTION, expense.getDescription())
				.addValue(Constants.SPargument.I_AMOUNT, expense.getAmount())
				.addValue(Constants.SPargument.I_MEMBER_IDS, expense.getMemberIds())
				.addValue(Constants.SPargument.I_EXPENSE_ID, expense.getExpenseId())
				.addValue(Constants.SPargument.I_OPERATION_STATE, expense.getOperationState());

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call param={}", expense);

		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		return output;
	}
	
	@Override
	public Map<String, Object> searchSplit(SplitDTO split) {
		try {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_EXPENSE_SEARCH)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_MEMBER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_TOGGLE_VIEW, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_START, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_LIMIT, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_RESULT, Types.REF_CURSOR, new SplitRowMapper()),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call state={}, split={}", split.getOperationState(),split);

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USER_ID, split.getUserId())
				.addValue(Constants.SPargument.I_GROUP_ID, split.getGroupId())
				.addValue(Constants.SPargument.I_MEMBER_ID, split.getMemberId())
				.addValue(Constants.SPargument.I_TOGGLE_VIEW, Constants.Value.ZERO)
				.addValue(Constants.SPargument.I_OPERATION_STATE, split.getOperationState())
				.addValue(Constants.SPargument.I_START, Constants.Value.START)
				.addValue(Constants.SPargument.I_LIMIT, Constants.Value.LIMIT);

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call result={}", result);
		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		@SuppressWarnings("unchecked")
		List<SplitDTO> splits = (List<SplitDTO>) result.get(Constants.SPargument.O_RESULT);
		SplitDTO split1 = (splits != null && !splits.isEmpty()) ? splits.get(0) : null;
		logger.debug("After procedure call split1={}", split1);
	    output.put("split", split1);
		return output;
		}
		catch(Exception ex) {
			logger.error("Exception in searchSplit: {}",ex);
			Map<String,Object> exc = new HashMap<>();
			exc.put(Constants.Param.statusCode, 999);
			exc.put(Constants.Param.message, ex.getMessage());
			return exc;
		}
	}
	@Override
	public Map<String, Object> searchSettlement(SettlementDTO settlement) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_SETTLEMENT_SEARCH)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_TOGGLE_VIEW     , Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_START, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_LIMIT, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_RESULT, Types.REF_CURSOR, new SettlementRowMapper()),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call state={}, user={}{}", settlement.getOperationState());

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_USER_ID, settlement.getUserId())
				.addValue(Constants.SPargument.I_GROUP_ID, settlement.getGroupId())
				.addValue(Constants.SPargument.I_TOGGLE_VIEW, settlement.getToggleView())
				.addValue(Constants.SPargument.I_OPERATION_STATE, settlement.getOperationState())
				.addValue(Constants.SPargument.I_START, Constants.Value.START)
				.addValue(Constants.SPargument.I_LIMIT, Constants.Value.LIMIT)
				;

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call settlement={}", settlement);
		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		output.put("settlement", result.get(Constants.SPargument.O_RESULT));
		return output;
	}

	@Override
	public Map<String, Object> manageSettlement(SettlementDTO settlement) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(Constants.StoredProcedure.PRC_SETTLEMENT_MANAGE)
				.withoutProcedureColumnMetaDataAccess().declareParameters(
						new SqlParameter(Constants.SPargument.I_USER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_GROUP_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_MEMBER_ID, Types.INTEGER),
						new SqlParameter(Constants.SPargument.I_AMOUNT, Types.DOUBLE),
						new SqlParameter(Constants.SPargument.I_OPERATION_STATE, Types.VARCHAR),
						new SqlParameter(Constants.SPargument.I_REQUEST_ID, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_CODE, Types.INTEGER),
						new SqlOutParameter(Constants.SPargument.O_STATUS_MESSAGE, Types.VARCHAR));
		logger.debug("Before procedure call state={}, param={}", settlement.getOperationState(), settlement);

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue(Constants.SPargument.I_GROUP_ID, settlement.getGroupId())
				.addValue(Constants.SPargument.I_USER_ID, settlement.getUserId())
				.addValue(Constants.SPargument.I_MEMBER_ID, settlement.getMemberId())
				.addValue(Constants.SPargument.I_AMOUNT, settlement.getAmount())
				.addValue(Constants.SPargument.I_OPERATION_STATE, settlement.getOperationState())
		.addValue(Constants.SPargument.I_REQUEST_ID, settlement.getRequestId());

		Map<String, Object> result = jdbcCall.execute(params);
		logger.debug("After procedure call param={}", settlement);

		Map<String, Object> output = new HashMap<>();
		output.put(Constants.Param.statusCode, result.get(Constants.SPargument.O_STATUS_CODE));
		output.put(Constants.Param.message, result.get(Constants.SPargument.O_STATUS_MESSAGE));
		return output;
	}

}
