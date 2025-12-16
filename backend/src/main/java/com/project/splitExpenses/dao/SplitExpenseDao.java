package com.project.splitExpenses.dao;

import java.util.Map;

import com.project.splitExpenses.dto.ExpenseDTO;
import com.project.splitExpenses.dto.SettlementDTO;
import com.project.splitExpenses.dto.SplitDTO;

public interface SplitExpenseDao {
	Map<String, Object> searchExpense(ExpenseDTO expense);

	Map<String, Object> manageExpense(ExpenseDTO expense);

	Map<String, Object> searchSplit(SplitDTO split);

	Map<String, Object> searchSettlement(SettlementDTO settlement);

	Map<String, Object> manageSettlement(SettlementDTO settlement);
}
