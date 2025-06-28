package in.techarray.billbuddy.expense_service.strategy;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;
import in.techarray.billbuddy.expense_service.model.SplitType;

@Component
public class ShareSplitStrategy implements SplitStrategy {

    @Override
    public List<ExpenseSplit> calculateSplits(ExpenseRequestDto expenseRequestDto) {
        Double totalShares = expenseRequestDto.getSplits().values().stream()
            .mapToDouble(Double::doubleValue).sum();
        List<ExpenseSplit> expenseSplits = expenseRequestDto.getSplits().entrySet().stream()
            .map( entry -> {
                Double amount = (entry.getValue() / totalShares) * expenseRequestDto.getTotalAmount();
                return ExpenseSplit.builder()
                    .userId(entry.getKey())
                    .amountOwed(amount)
                    .build();
            }).collect(Collectors.toList());
        return expenseSplits;
    }

    @Override
    public SplitType getType() {
        return SplitType.SHARE_BASED;
    }

}
