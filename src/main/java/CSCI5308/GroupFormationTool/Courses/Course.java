package CSCI5308.GroupFormationTool.Courses;

import java.util.List;

import CSCI5308.GroupFormationTool.AccessControl.CurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.ICurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.AccessControl.User;

public class Course
{
	private long id;
	private String title;

	public Course()
	{
		setDefaults();
	}

	public Course(long id, ICoursePersistence courseDB)
	{
		setDefaults();
		courseDB.loadCourseByID(id, this);
	}

	public void setDefaults()
	{
		id = -1;
		title = "";
	}
	
	public void setId(long id)
	{
		this.id = id;
	}

	public long getId()
	{
		return id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}

	public boolean delete(ICoursePersistence courseDB)
	{
		return courseDB.deleteCourse(id);
	}

	public boolean createCourse(ICoursePersistence courseDB)
	{
		return courseDB.createCourse(this);
	}

	public boolean enrollUserInCourse(Role role, User user, ICourseUserRelationship userRelationship)
	{
		return userRelationship.enrollUserInCourse(user, this, role);
	}

	public boolean isCurrentUserEnrolledAsRoleInCourse(ICurrentUser<User> currentUser, Role role,
													   ICourseUserRelationship userRelationship)
	{
		return userRelationship.userHasRoleInCourse(currentUser.getCurrentAuthenticatedUser(), role, this);
	}

	public boolean isCurrentUserEnrolledAsRoleInCourse(ICurrentUser<User> currentUser, String role,
													   ICourseUserRelationship userRelationship, IUserPersistence userPersistence)
	{
		Role r = Role.valueOf(role.toUpperCase());
		return isCurrentUserEnrolledAsRoleInCourse(currentUser, r, userRelationship);
	}

	public List<Role> getAllRolesForCurrentUserInCourse(ICurrentUser<User> currentUser,
														ICourseUserRelationship userRelationship
	)
	{
		return userRelationship.loadAllRoluesForUserInCourse(currentUser.getCurrentAuthenticatedUser(), this);
	}
}
