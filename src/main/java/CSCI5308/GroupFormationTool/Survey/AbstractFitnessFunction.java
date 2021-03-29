package CSCI5308.GroupFormationTool.Survey;

import java.util.List;

public abstract class AbstractFitnessFunction
{
    public abstract double getFitnessScore(Rule rule,
                                           Group group,
                                           Double reward, List<SurveyResponse> responses);
}
