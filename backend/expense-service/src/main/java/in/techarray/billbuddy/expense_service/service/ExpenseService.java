package in.techarray.billbuddy.expense_service.service;

import java.util.List;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.Expense;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;
import in.techarray.billbuddy.expense_service.repository.ExpenseRepository;
import in.techarray.billbuddy.expense_service.repository.ExpenseSplitRepository;

public class ExpenseService {
    private ExpenseRepository expenseRepository;
    private ExpenseSplitRepository expenseSplitRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense createExpenseWithSplits( ExpenseRequestDto expenseRequestDto ) {
        Expense expense = Expense.builder()
            .description(expenseRequestDto.getDescription())
            .createdByUserId(expenseRequestDto.getCreatedByUserId())
            .totalAmount(expenseRequestDto.getTotalAmount())
            .date(expenseRequestDto.getDate())
            .splitType(expenseRequestDto.getSplitType()).build();

            Expense savedExpense = expenseRepository.save(expense);

        switch( expenseRequestDto.getSplitType() ){
            case EQUAL:
                Double splitAmount = expenseRequestDto.getTotalAmount() / expenseRequestDto.getParticipantUserIds().size();
                List<ExpenseSplit> expenseSplits = expenseRequestDto.getParticipantUserIds().stream()
                    .map( userId -> ExpenseSplit.builder()
                        .expenseId( savedExpense.getId() )
                        .userId( userId )
                        .amountOwed(splitAmount)
                        .build()).toList();
                expenseSplitRepository.saveAll( expenseSplits );
                break;
            default:
                break;
        }

        return savedExpense;
    }


}
