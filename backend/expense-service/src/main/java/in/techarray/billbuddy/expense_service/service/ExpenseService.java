package in.techarray.billbuddy.expense_service.service;

import java.util.List;
import java.util.UUID;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.dto.UserExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.Expense;

public interface ExpenseService {

    public Expense createExpenseWithSplits( ExpenseRequestDto expenseRequestDto );
    public Expense getExpenseById(UUID id);
    public Expense updateExpense(UUID id, ExpenseRequestDto expenseRequestDto);
    void deleteExpense(UUID id);
    public List<Expense> getAllExpensesByUser(UUID userId);
}
