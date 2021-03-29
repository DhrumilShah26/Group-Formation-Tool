package CSCI5308.GroupFormationTool.CoursesTest;

import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.Courses.Course;
import CSCI5308.GroupFormationTool.Courses.ICourseUserRelationship;
import CSCI5308.GroupFormationTool.Courses.Role;

import java.util.List;

public class CourseUserRelationshipMock implements ICourseUserRelationship {
    @Override
    public boolean userHasRoleInCourse(User user, Role role, Course course) {
        return false;
    }

    @Override
    public List<Role> loadAllRoluesForUserInCourse(User user, Course course) {
        return null;
    }

    @Override
    public boolean enrollUserInCourse(User user, Course course, Role role) {
        return false;
    }
}
