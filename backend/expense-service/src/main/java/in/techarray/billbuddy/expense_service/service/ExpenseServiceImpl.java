package in.techarray.billbuddy.expense_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.exception.ExpenseNotFoundException;
import in.techarray.billbuddy.expense_service.model.Expense;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;
import in.techarray.billbuddy.expense_service.repository.ExpenseRepository;
import in.techarray.billbuddy.expense_service.repository.ExpenseSplitRepository;
import in.techarray.billbuddy.expense_service.strategy.SplitStrategy;
import in.techarray.billbuddy.expense_service.strategy.SplitStrategyFactory;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService{
    private ExpenseRepository expenseRepository;
    private ExpenseSplitRepository expenseSplitRepository;
    private SplitStrategyFactory strategyFactory;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpenseWithSplits( ExpenseRequestDto expenseRequestDto ) {

        SplitStrategy strategy = strategyFactory.getStrategy(expenseRequestDto.getSplitType());
        List<ExpenseSplit> splits = strategy.calculateSplits(expenseRequestDto);

        Expense expense = Expense.builder()
            .description(expenseRequestDto.getDescription())
            .totalAmount(expenseRequestDto.getTotalAmount())
            .date(expenseRequestDto.getDate())
            .createdByUserId(expenseRequestDto.getCreatedByUserId())
            .splitType(expenseRequestDto.getSplitType())
            .expenseSplits(splits)
            .build();
        Expense savedExpense = expenseRepository.save(expense);

        return savedExpense;
    }

    public Expense getExpenseById( UUID id ){
        return expenseRepository.findById(id)
            .orElseThrow( () -> new ExpenseNotFoundException("Expense not found: " + id) );
    }

    @Override
    public Expense updateExpense(UUID id, ExpenseRequestDto expenseRequestDto) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow( () -> new ExpenseNotFoundException("Expense not found: " + id) );

        SplitStrategy strategy = strategyFactory.getStrategy(expenseRequestDto.getSplitType());
        List<ExpenseSplit> updatedSplits = strategy.calculateSplits(expenseRequestDto);

        expense.setDescription(expenseRequestDto.getDescription());
        expense.setDate(expenseRequestDto.getDate());
        expense.setTotalAmount(expenseRequestDto.getTotalAmount());
        expense.setSplitType(expenseRequestDto.getSplitType());
        expense.setExpenseSplits(updatedSplits);

        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(UUID id) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow( () -> new ExpenseNotFoundException("Expense not found: " + id) );
        expenseRepository.delete(expense);
    }
}
