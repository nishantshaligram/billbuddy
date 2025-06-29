package in.techarray.billbuddy.expense_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.dto.UserExpenseRequestDto;
import in.techarray.billbuddy.expense_service.exception.ExpenseNotFoundException;
import in.techarray.billbuddy.expense_service.model.Expense;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;
import in.techarray.billbuddy.expense_service.repository.ExpenseRepository;
import in.techarray.billbuddy.expense_service.strategy.SplitStrategy;
import in.techarray.billbuddy.expense_service.strategy.SplitStrategyFactory;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService{
    private final ExpenseRepository expenseRepository;
    private final SplitStrategyFactory strategyFactory;

    @Override
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
        
        for (ExpenseSplit split : splits) {
            split.setExpense(expense);
        }

        Expense savedExpense = expenseRepository.save(expense);

        return savedExpense;
    }

    @Override
    public Expense getExpenseById( UUID id ){
        return expenseRepository.findById(id)
            .orElseThrow( () -> new ExpenseNotFoundException("Expense not found: " + id) );
    }

    @Override
    public Expense updateExpense(UUID id, ExpenseRequestDto expenseRequestDto) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow( () -> new ExpenseNotFoundException("Expense not found: " + id) );

        SplitStrategy strategy = strategyFactory.getStrategy(expenseRequestDto.getSplitType());
        List<ExpenseSplit> newSplits = strategy.calculateSplits(expenseRequestDto);

        expense.setDescription(expenseRequestDto.getDescription());
        expense.setDate(expenseRequestDto.getDate());
        expense.setTotalAmount(expenseRequestDto.getTotalAmount());
        expense.setSplitType(expenseRequestDto.getSplitType());
        expense.getExpenseSplits().clear();

        for (ExpenseSplit split : newSplits) {
            split.setExpense(expense);
            expense.getExpenseSplits().add(split);
        }

        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(UUID id) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow( () -> new ExpenseNotFoundException("Expense not found: " + id) );
        expenseRepository.delete(expense);
    }

    @Override
    public List<Expense> getAllExpensesByUser(UserExpenseRequestDto userExpenseRequestDto) {
        return expenseRepository.findByCreatedByUserId(userExpenseRequestDto.getUserId());
    }
}
