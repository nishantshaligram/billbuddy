package in.techarray.billbuddy.expense_service.service;

import java.util.List;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.Expense;
import in.techarray.billbuddy.expense_service.repository.ExpenseRepository;

public class ExpenseService {
    private ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpense( ExpenseRequestDto expenseRequestDto ) {
        Expense expense = Expense.builder()
            .description(expenseRequestDto.getDescription())
            .createdByUserId(expenseRequestDto.getCreatedByUserId())
            .amount(expenseRequestDto.getAmount())
            .date(expenseRequestDto.getDate())
            .splitType(expenseRequestDto.getSplitType()).build();

        return expenseRepository.save(expense);
    }


}
