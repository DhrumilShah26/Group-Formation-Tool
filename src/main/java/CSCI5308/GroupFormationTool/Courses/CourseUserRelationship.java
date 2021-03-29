package CSCI5308.GroupFormationTool.Courses;

import java.util.List;

import CSCI5308.GroupFormationTool.AccessControl.*;

public class CourseUserRelationship implements ICourseUserRelationship
{
	ICourseUserRelationshipPersistence persistence;

	public CourseUserRelationship(ICourseUserRelationshipPersistence persistence) {
		this.persistence = persistence;
	}

	public boolean userHasRoleInCourse(User user, Role role, Course course)
	{
		if (null == user || user.isValidUser() == false)
		{
			return false;
		}
		if (null == role)
		{
			return false;
		}
		if (null == course)
		{
			return false;
		}
		List<Role> roles = persistence.loadUserRolesForCourse(course, user);
		if(null == roles)
		{
			return false;
		}
		return roles.contains(role);
	}

	public List<Role> loadAllRoluesForUserInCourse(User user, Course course)
	{
		List<Role> roles = persistence.loadUserRolesForCourse(course, user);
		return roles;
	}

	public boolean enrollUserInCourse(User user, Course course, Role role)
	{
		return persistence.enrollUser(course, user, role);
	}
}
