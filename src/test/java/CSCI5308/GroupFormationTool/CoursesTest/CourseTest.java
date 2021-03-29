package CSCI5308.GroupFormationTool.CoursesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.Courses.Course;
import CSCI5308.GroupFormationTool.Courses.ICoursePersistence;

@SpringBootTest
@SuppressWarnings("deprecation")
class CourseTest {

	static IGlobalFactoryProvider provider;
	static ICoursePersistence coursePersistence;

	@BeforeAll
	static void setup() {
		provider = new GlobalDependencyMock();
		coursePersistence = provider.getCourseDependencyFactory().getCoursePersistence();
	}

	@Test
	public void ConstructorTests() {
		Course course = new Course();
		assertEquals(course.getId(), -1);
		assertEquals(course.getTitle(), "");

		course = new Course(0, coursePersistence);
		assertEquals(course.getId(), 0);
		assertEquals(course.getTitle(), "Software Engineering");
	}

	@Test
	public void setIdTest() {
		Course course = new Course();
		course.setId(7);
		assertEquals(course.getId(), 7);
	}

	@Test
	public void getIdTest() {
		Course course = new Course();
		course.setId(7);
		assertEquals(course.getId(), 7);
	}

	@Test
	public void setTitleTest() {
		Course course = new Course();
		course.setTitle("Advanced Topics in Software Development");
		assertEquals(course.getTitle(), ("Advanced Topics in Software Development"));
	}

	@Test
	public void getTitleTest() {
		Course course = new Course();
		course.setTitle("Advanced Topics in Software Development");
		assertEquals(course.getTitle(), "Advanced Topics in Software Development");
	}

	@Test
	public void deleteCourseTest() {

		Course course = new Course();
		course.setId(5);
		boolean status = course.delete(coursePersistence);
		assertTrue(status);
	}

	@Test
	public void createCourseTest() {

		Course course = new Course();
		course.setId(0);
		course.setTitle("Software Engineering");
		course.createCourse(coursePersistence);
		assertEquals(course.getId(), 0);
		assertEquals(course.getTitle(), "Software Engineering");
	}

	@Test
	public void getAllCourseTest() {

		List<Course> courseList = new ArrayList<>();
		Course course = new Course();
		course.setId(0);
		course.setTitle("Software Engineering");
		courseList.add(course);
		course = new Course();
		course.setId(1);
		course.setTitle("Advanced Topics in Software Development");
		courseList.add(course);
		List<Course> courseListTest = coursePersistence.loadAllCourses();
		String courseTitleTest = courseListTest.get(0).getTitle();
		String courseTitle = courseList.get(0).getTitle();
		assertEquals(courseTitleTest, courseTitle);

	}

}
