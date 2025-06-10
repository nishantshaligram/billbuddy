package in.techarray.billbuddy.expense_service.strategy;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;

@Component
public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public List<ExpenseSplit> calculateSplits(UUID expenseId, ExpenseRequestDto expenseRequestDto) {
        Double splitAmount = expenseRequestDto.getTotalAmount() / expenseRequestDto.getParticipantUserIds().size();
        List<ExpenseSplit> expenseSplits = expenseRequestDto.getParticipantUserIds().stream()
            .map( userId -> ExpenseSplit.builder()
                .expenseId(expenseId)
                .userId(userId)
                .amountOwed(splitAmount)
                .build()).toList();
        return expenseSplits;
    }

}
