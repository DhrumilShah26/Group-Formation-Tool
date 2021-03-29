package CSCI5308.GroupFormationTool.CoursesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.Courses.Course;
import CSCI5308.GroupFormationTool.Courses.Role;

@SpringBootTest
class StudentCSVImportTest {

	@Test
	public void enrollStudentFromRecord() {
		User user = new User();
		Course course = new Course();
		Assertions.assertFalse(course.enrollUserInCourse(Role.STUDENT, user, new CourseUserRelationshipMock()));
	}

	@Test
	public void getSuccessResults() {
		List<String> successResults = new ArrayList<>();
		successResults.add("Created record");
		assertThat(successResults).isNotNull();
		assertThat(successResults).isNotEmpty();
		assertEquals(successResults.size(), 1);
	}

	@Test
	public void getFailureResults() {
		List<String> failureResults = new ArrayList<>();
		failureResults.add("Created record");
		assertThat(failureResults).isNotNull();
		assertThat(failureResults).isNotEmpty();
		assertEquals(failureResults.size(), 1);
	}

}
