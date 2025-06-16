package in.techarray.billbuddy.expense_service.strategy;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.techarray.billbuddy.expense_service.model.SplitType;

@Component
public class SplitStrategyFactory {
    private final Map<SplitType, SplitStrategy> strategies;

    @Autowired
    public SplitStrategyFactory(List<SplitStrategy> strategyList) {
        strategies = new EnumMap<>(SplitType.class);
        strategyList.forEach( s -> strategies.put(s.getType(), s) );
    }

    public SplitStrategy getStrategy(SplitType type) {
        return strategies.get(type);
    }
}
