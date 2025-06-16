package in.techarray.billbuddy.expense_service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import in.techarray.billbuddy.expense_service.model.SplitType;

@ExtendWith(MockitoExtension.class)
public class SplitStrategyFactoryTest {
    @Mock private EqualSplitStrategy equalSplitStrategy;
    @Mock private ExactSplitStrategy exactSplitStrategy;
    @Mock private PercentageSplitStrategy percentageSplitStrategy;

    private SplitStrategyFactory factory;

    @BeforeEach
    void setUp(){
        Mockito.when(equalSplitStrategy.getType()).thenReturn(SplitType.EQUAL);

        Mockito.when(exactSplitStrategy.getType()).thenReturn(SplitType.EXACT);
        Mockito.when(percentageSplitStrategy.getType()).thenReturn(SplitType.PERCENTAGE);

        factory = new SplitStrategyFactory(
            List.of(equalSplitStrategy, exactSplitStrategy, percentageSplitStrategy)
        );
    }

    @Test
    void shouldReturnCorrectStrategy() {
        assertEquals(equalSplitStrategy, factory.getStrategy(SplitType.EQUAL));
        assertEquals(exactSplitStrategy, factory.getStrategy(SplitType.EXACT));
        assertEquals(percentageSplitStrategy, factory.getStrategy(SplitType.PERCENTAGE));
    }

    @Test
    void shouldReturnNullForUnknownStrategy() {
        assertNull(factory.getStrategy(SplitType.SHARE_BASED)); // Assuming SHARE isn't registered
    }
}
