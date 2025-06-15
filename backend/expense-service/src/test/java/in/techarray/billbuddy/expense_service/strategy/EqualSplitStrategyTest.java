package in.techarray.billbuddy.expense_service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import in.techarray.billbuddy.expense_service.dto.ExpenseRequestDto;
import in.techarray.billbuddy.expense_service.model.ExpenseSplit;

public class EqualSplitStrategyTest {
    private final EqualSplitStrategy strategy = new EqualSplitStrategy();

    @Test
    void shouldSplitEqually(){
        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setTotalAmount(100.0);
        expenseRequestDto.setParticipantUserIds(List.of(
            UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()
        ));
        
        List<ExpenseSplit> expenseSplits = strategy.calculateSplits(UUID.randomUUID(), expenseRequestDto);

        assertEquals(3, expenseSplits.size());
        expenseSplits.forEach(split -> assertEquals( 100.0/3, split.getAmountOwed(), 0.001));
    }

    @Test
    void shouldHandleSingleUser() {
        UUID userID = UUID.randomUUID();
        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        expenseRequestDto.setTotalAmount(75.0);
        expenseRequestDto.setParticipantUserIds(List.of(userID));

        List<ExpenseSplit> expenseSplits = strategy.calculateSplits(userID, expenseRequestDto);
        
        assertEquals(1, expenseSplits.size());
        assertEquals(75.0, expenseSplits.get(0).getAmountOwed(), 0.001);
    }
}
