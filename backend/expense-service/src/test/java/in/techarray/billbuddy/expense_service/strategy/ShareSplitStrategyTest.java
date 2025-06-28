package in.techarray.billbuddy.expense_service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;

public class ShareSplitStrategyTest {

    private final ShareSplitStrategy strategy = new ShareSplitStrategy();

    @Test
    void shouldSplitBasedOnShares() {
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();

        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setTotalAmount(180.0);
        expenseRequestDto.setSplits(Map.of(
            user1, 2.0,
            user2, 4.0
        ));
        
        List<ExpenseSplit> expenseSplits = strategy.calculateSplits(expenseRequestDto);

        assertEquals(2, expenseSplits.size());
        assertTrue(expenseSplits.stream().anyMatch( split -> split.getUserId().equals(user1) &&
            Math.abs(split.getAmountOwed() -60.0) < 0.001), "Expected split for user1 with amount 60.0 not found.");
        assertTrue(expenseSplits.stream().anyMatch( split -> split.getUserId().equals(user2) &&
            Math.abs(split.getAmountOwed() -120.0) < 0.001), "Expected split for user1 with amount 60.0 not found.");
    }

    
}
