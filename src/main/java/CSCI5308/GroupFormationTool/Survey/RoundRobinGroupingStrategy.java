package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinGroupingStrategy extends AbstractGroupingStrategy
{
    private boolean isInValid(Integer numberOfGroups, List<SurveyResponse> responses) {
        return numberOfGroups > responses.size();
    }

    @Override
    public List<Group> makeGroups(Integer numberOfGroups, List<SurveyResponse> responses)
    {
        if(isInValid(numberOfGroups, responses)) {
            return new ArrayList<>();
        }
        List<Group> groups = Group.makeEmptyGroups(numberOfGroups);
        int i = 0;
        for (SurveyResponse r :
                responses) {
            groups.get(i).addMember(r.getUserID());
            i = (i+1) % numberOfGroups;
        }
        return groups;
    }
}
