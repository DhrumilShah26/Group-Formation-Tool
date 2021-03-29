package CSCI5308.GroupFormationTool.Survey;

import java.util.List;

public class GroupingStrategyFactory extends AbstractGroupingStrategyFactory {
	@Override
	public AbstractGroupingStrategy getDefaultStrategy() {
		return new RoundRobinGroupingStrategy();
	}

	@Override
	public AbstractGroupingStrategy makeHeuristicBasedGroupingStrategy(List<Rule> rules) {
		return new HeuristicBasedGroupingStrategy();
	}
}
