package in.techarray.billbuddy.expense_service.strategy;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.techarray.billbuddy.expense_service.model.SplitType;

@Component
public class SplitStrategyFactory {
    private final Map<SplitType, SplitStrategy> strategyMap = new EnumMap<>(SplitType.class);

    @Autowired
    public SplitStrategyFactory(EqualSplitStrategy equal, ExactSplitStrategy exact, PercentageSplitStrategy percentage, ShareSplitStrategy share) {
        strategyMap.put(SplitType.EQUAL, equal);
        strategyMap.put(SplitType.EXACT, exact);
        strategyMap.put(SplitType.PERCENTAGE, percentage);
        strategyMap.put(SplitType.SHARE_BASED, share);
    }

    public SplitStrategy getStrategy(SplitType type){
        return strategyMap.get(type);
    }
}
