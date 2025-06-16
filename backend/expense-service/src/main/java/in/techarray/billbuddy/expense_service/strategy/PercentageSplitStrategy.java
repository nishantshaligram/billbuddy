package in.techarray.billbuddy.expense_service.strategy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;
import in.techarray.billbuddy.expense_service.model.SplitType;

@Component
public class PercentageSplitStrategy implements SplitStrategy{

    @Override
    public List<ExpenseSplit> calculateSplits(UUID expenseId, ExpenseRequestDto expenseRequestDto) {
        Double totalPercent = expenseRequestDto.getPercentageSplits().values().stream()
            .mapToDouble(Double::doubleValue).sum();
        if( Math.abs(totalPercent - 100.0) > 0.01 ){
            throw new IllegalArgumentException( "Percentages must sum to 100" );
        }
        List<ExpenseSplit> expenseSplits = expenseRequestDto.getPercentageSplits().entrySet().stream()
            .map( entry -> {
                Double amount = entry.getValue() / 100.0 * expenseRequestDto.getTotalAmount();
                return new ExpenseSplit(null, expenseId, entry.getKey(), amount);
            } ).collect(Collectors.toList());
        return expenseSplits;
    }

    @Override
    public SplitType getType() {
        return SplitType.PERCENTAGE;
    }

}
