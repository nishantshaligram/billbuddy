package in.techarray.billbuddy.expense_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.Expense;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;
import in.techarray.billbuddy.expense_service.repository.ExpenseRepository;
import in.techarray.billbuddy.expense_service.repository.ExpenseSplitRepository;
import in.techarray.billbuddy.expense_service.strategy.EqualSplitStrategy;
import in.techarray.billbuddy.expense_service.strategy.SplitStrategy;
import in.techarray.billbuddy.expense_service.strategy.SplitStrategyFactory;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private ExpenseRepository expenseRepository;
    private ExpenseSplitRepository expenseSplitRepository;
    private SplitStrategyFactory strategyFactory;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpenseWithSplits( ExpenseRequestDto expenseRequestDto ) {
        Expense expense = Expense.builder()
            .description(expenseRequestDto.getDescription())
            .totalAmount(expenseRequestDto.getTotalAmount())
            .date(expenseRequestDto.getDate())
            .createdByUserId(expenseRequestDto.getCreatedByUserId())
            .splitType(expenseRequestDto.getSplitType())
            .build();

        Expense savedExpense = expenseRepository.save(expense);

        SplitStrategy strategy = strategyFactory.getStrategy(expenseRequestDto.getSplitType());
        List<ExpenseSplit> splits = strategy.calculateSplits(savedExpense.getId(), expenseRequestDto);
        return savedExpense;
    }


}
