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

public class PercentageSplitStrategyTest {

    private final PercentageSplitStrategy strategy = new PercentageSplitStrategy();

    @Test
    void shouldSplitBasedOnPercentage() {
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();

        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setTotalAmount(200.0);
        expenseRequestDto.setPercentageSplits(Map.of(
            user1, 25.0,
            user2, 75.0
        ));

        List<ExpenseSplit> expenseSplits = strategy.calculateSplits(expenseRequestDto);

        assertEquals(2, expenseSplits.size());

        // Check for the split for user2 with 25.0% = 50
        assertTrue(expenseSplits.stream()
                        .anyMatch(split -> split.getUserId().equals(user1) &&
                                           Math.abs(split.getAmountOwed() - 50.0) < 0.001),
                "Expected split for user1 with amount 40.0 not found.");

        // Check for the split for user2 with 75.0% = 150
        assertTrue(expenseSplits.stream()
                        .anyMatch(split -> split.getUserId().equals(user2) &&
                                           Math.abs(split.getAmountOwed() - 150.0) < 0.001),
                "Expected split for user2 with amount 80.0 not found.");
    }

    @Test
    void shouldFailIfPercentageMismatch() {
         UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();

        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setTotalAmount(200.0);
        expenseRequestDto.setPercentageSplits(Map.of(
            user1, 40.0,
            user2, 40.0
        ));

        assertThrows(IllegalArgumentException.class, () -> strategy.calculateSplits( expenseRequestDto));
    }
}
