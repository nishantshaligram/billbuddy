package in.techarray.billbuddy.expense_service.strategy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;
import in.techarray.billbuddy.expense_service.model.SplitType;

@Component
public class ExactSplitStrategy  implements SplitStrategy{

    @Override
    public List<ExpenseSplit> calculateSplits(ExpenseRequestDto expenseRequestDto) {
        Double total = expenseRequestDto.getExactAmounts().values().stream().mapToDouble(
            Double::doubleValue).sum();
        if(Math.abs(total-expenseRequestDto.getTotalAmount()) > 0.01){
            throw new IllegalArgumentException("Exact amounts must sum to total");
        }

        List<ExpenseSplit> expenseSplits = expenseRequestDto.getExactAmounts().entrySet().stream()
            .map(entry -> ExpenseSplit.builder()
                .userId(entry.getKey())
                .amountOwed(entry.getValue())
                .build()).collect(Collectors.toList());
        return expenseSplits;
    }

    @Override
    public SplitType getType() {
        return SplitType.EXACT;
    }

}
