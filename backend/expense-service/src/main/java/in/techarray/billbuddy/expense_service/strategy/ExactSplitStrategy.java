package in.techarray.billbuddy.expense_service.strategy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;

public class ExactSplitStrategy  implements SplitStrategy{

    @Override
    public List<ExpenseSplit> calculateSplits(UUID expenseId, ExpenseRequestDto expenseRequestDto) {
        Double total = expenseRequestDto.getExactAmounts().values().stream().mapToDouble(
            Double::doubleValue).sum();
        if(Math.abs(total-expenseRequestDto.getTotalAmount()) > 0.01){
            throw new IllegalArgumentException("Exact amounts must sum to total");
        }

        List<ExpenseSplit> expenseSplits = expenseRequestDto.getExactAmounts().entrySet().stream()
            .map(entry -> new ExpenseSplit(null, expenseId, entry.getKey(), entry.getValue())).collect(Collectors.toList());
        return expenseSplits;
    }

}
