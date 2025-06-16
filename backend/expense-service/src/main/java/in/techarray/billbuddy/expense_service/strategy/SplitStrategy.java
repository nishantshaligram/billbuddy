package in.techarray.billbuddy.expense_service.strategy;

import java.util.List;
import java.util.UUID;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;
import in.techarray.billbuddy.expense_service.model.SplitType;

public interface SplitStrategy {
    SplitType getType();
    List<ExpenseSplit> calculateSplits( UUID expenseId, ExpenseRequestDto expenseRequestDto );
}
