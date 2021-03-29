package CSCI5308.GroupFormationTool.CoursesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.AccessControl.ICurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.AccessControlTest.CurrentUserMock;
import CSCI5308.GroupFormationTool.AccessControlTest.UserDBMock;
import CSCI5308.GroupFormationTool.Courses.Course;
import CSCI5308.GroupFormationTool.Courses.CourseUserRelationship;
import CSCI5308.GroupFormationTool.Courses.ICourseUserRelationshipPersistence;
import CSCI5308.GroupFormationTool.Courses.Role;

@SpringBootTest
class CourseUserRelationshipTest {
	private final ICourseUserRelationshipPersistence courseUserRelationshipDB;
	ICurrentUser<User> currentUser;

	public CourseUserRelationshipTest() {
		IGlobalFactoryProvider provider = new GlobalDependencyMock();
		courseUserRelationshipDB = provider.getCourseDependencyFactory().getCourseUserRelationshipPersistence();
		currentUser = provider.getAclDependencyFactory().getCurrentUser();
	}

	@Test
	public void userHasRoleInCourse() {
		Course course = new Course();
		course.setId(0);
		CourseUserRelationship courseUserRelationship = new CourseUserRelationship(courseUserRelationshipDB);

		User user = currentUser.getCurrentAuthenticatedUser();
		List<Role> roles = courseUserRelationshipDB.loadUserRolesForCourse(course, user);
		assertThat(roles).isNotNull();
		assertThat(roles).isNotEmpty();
		assertNotEquals(roles.size(), 0);
		courseUserRelationship.enrollUserInCourse(user, course, Role.STUDENT);
		courseUserRelationship.enrollUserInCourse(user, course, Role.TA);
		assertTrue(courseUserRelationship.userHasRoleInCourse(user, Role.TA, course));
		assertFalse(courseUserRelationship.userHasRoleInCourse(null, Role.TA, course));
		assertFalse(courseUserRelationship.userHasRoleInCourse(user, null, course));
		assertFalse(courseUserRelationship.userHasRoleInCourse(user, Role.ADMIN, null));
		user = new User();
		user.setId(9999);
		assertFalse(courseUserRelationship.userHasRoleInCourse(user, Role.TA, course));
		user.setId(-1);
		assertFalse(courseUserRelationship.userHasRoleInCourse(user, Role.TA, course));

	}

	@Test
	public void loadAllRoluesForUserInCourse() {
		IUserPersistence userPersistence = new UserDBMock();
		Course course = new Course();
		course.setId(0);
		CurrentUserMock currentUser = new CurrentUserMock();
		User user = currentUser.getCurrentAuthenticatedUser();
		CourseUserRelationship courseUserRelationship = new CourseUserRelationship(courseUserRelationshipDB);
		List<Role> roles = courseUserRelationship.loadAllRoluesForUserInCourse(user, course);
		assertNotEquals(roles.size(), 0);
	}

	@Test
	public void enrollUserInCourse() {
		Course course = new Course();
		CurrentUserMock currentUser = new CurrentUserMock();
		User user = currentUser.getCurrentAuthenticatedUser();
		boolean result = courseUserRelationshipDB.enrollUser(course, user, Role.STUDENT);
		assertTrue(result);
	}

}
