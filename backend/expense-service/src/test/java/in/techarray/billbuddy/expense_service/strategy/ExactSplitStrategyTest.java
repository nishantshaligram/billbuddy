package in.techarray.billbuddy.expense_service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;

public class ExactSplitStrategyTest {

    private final ExactSplitStrategy strategy = new ExactSplitStrategy();

    @Test
    void shouldSplitAsSpecified() {
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();

        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setTotalAmount(120.0);
        expenseRequestDto.setExactAmounts(Map.of(
            user1, 40.0,
            user2, 80.0
        ));

        List<ExpenseSplit> expenseSplits = strategy.calculateSplits(UUID.randomUUID(), expenseRequestDto);

        assertEquals(2, expenseSplits.size());
        assertTrue(expenseSplits.stream()
                        .anyMatch(split -> split.getUserId().equals(user1) &&
                                           Math.abs(split.getAmountOwed() - 40.0) < 0.001),
                "Expected split for user1 with amount 40.0 not found.");

        // Check for the split for user2 with 80.0
        assertTrue(expenseSplits.stream()
                        .anyMatch(split -> split.getUserId().equals(user2) &&
                                           Math.abs(split.getAmountOwed() - 80.0) < 0.001),
                "Expected split for user2 with amount 80.0 not found.");
    }

    @Test
    void shouldFailIfAmountsMismatch(){
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();

        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setTotalAmount(100.0);
        expenseRequestDto.setExactAmounts(Map.of(
            user1, 30.0,
            user2, 60.0
        ));

        assertThrows(IllegalArgumentException.class, () -> strategy.calculateSplits(UUID.randomUUID(), expenseRequestDto));
    }
}
