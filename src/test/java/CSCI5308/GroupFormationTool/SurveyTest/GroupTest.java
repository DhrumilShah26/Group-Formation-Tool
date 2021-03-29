package CSCI5308.GroupFormationTool.SurveyTest;

import CSCI5308.GroupFormationTool.Survey.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GroupTest
{
    @Test
    public void makeEmptyGroupTest() {
        List<Group> groups =  Group.makeEmptyGroups(5);
        Assertions.assertEquals(groups.size(), 5);
    }

    @Test
    public void getUserIdsTest() {
        Group g = new Group();
        g.addMember(1);
        Assertions.assertEquals(g.getUserIds().size(), 1);
    }

    @Test
    public void hasMemberTest() {
        Group g = new Group();
        g.addMember(1);
        Assertions.assertTrue(g.hasMember(1));
    }

    @Test
    public void setUserIdsTest() {
        Group g = new Group();

    }

}
