package in.techarray.billbuddy.expense_service.strategy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;

@Component
public class ShareSplitStrategy implements SplitStrategy {

    @Override
    public List<ExpenseSplit> calculateSplits(UUID expenseId, ExpenseRequestDto expenseRequestDto) {
        Integer totalShares = expenseRequestDto.getShareSplits().values().stream()
            .mapToInt(Integer::intValue).sum();
        List<ExpenseSplit> expenseSplits = expenseRequestDto.getShareSplits().entrySet().stream()
            .map( entry -> {
                Double amount = (Double.valueOf(entry.getValue()) / totalShares) * expenseRequestDto.getTotalAmount();
                return new ExpenseSplit(null, expenseId, entry.getKey(), amount);
            }).collect(Collectors.toList());
        return expenseSplits;
    }

}
