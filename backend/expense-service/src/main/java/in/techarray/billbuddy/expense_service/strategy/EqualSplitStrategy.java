package in.techarray.billbuddy.expense_service.strategy;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;
import in.techarray.billbuddy.expense_service.model.SplitType;

@Component
public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public List<ExpenseSplit> calculateSplits(ExpenseRequestDto expenseRequestDto) {
        Double splitAmount = expenseRequestDto.getTotalAmount() / expenseRequestDto.getParticipantUserIds().size();
        List<ExpenseSplit> expenseSplits = expenseRequestDto.getParticipantUserIds().stream()
            .map( userId -> ExpenseSplit.builder()
                .userId(userId)
                .amountOwed(splitAmount)
                .build()).toList();
        return expenseSplits;
    }

    @Override
    public SplitType getType() {
        return SplitType.EQUAL;
    }

}
