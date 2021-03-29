package CSCI5308.GroupFormationTool.Survey;

import java.util.List;

public abstract class AbstractGroupingStrategyFactory
{
    public abstract AbstractGroupingStrategy getDefaultStrategy();
    public abstract AbstractGroupingStrategy makeHeuristicBasedGroupingStrategy(List<Rule> rules);
}
