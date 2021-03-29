package CSCI5308.GroupFormationTool.Survey;



import java.util.List;

public abstract class AbstractGroupingStrategy
{
    public List<Group> makeGroups(Integer numberOfGroups, List<SurveyResponse> responses, List<Rule> rules) {
        return makeGroups(numberOfGroups, responses);
    };
    public List<Group> makeGroups(Integer numberOfGroups, List<SurveyResponse> responses) {
        return Group.makeEmptyGroups(numberOfGroups);
    };
}
