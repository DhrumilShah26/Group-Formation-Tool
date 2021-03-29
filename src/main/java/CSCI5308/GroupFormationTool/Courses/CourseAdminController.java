package CSCI5308.GroupFormationTool.Courses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.AccessControl.User;

@Controller
public class CourseAdminController {

	private final Logger logger = LoggerFactory.getLogger(CourseAdminController.class);
	final AbstractCourseDependencyFactory courseDependencyFactory;

	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String INSTRUCTOR = "instructor";

	public CourseAdminController(IGlobalFactoryProvider globalFactoryProvider) {
		this.courseDependencyFactory = globalFactoryProvider.getCourseDependencyFactory();
	}

	@GetMapping("/admin/course")
	public String course(Model model) {
		ICoursePersistence courseDB = courseDependencyFactory.getCoursePersistence();
		List<Course> allCourses = new ArrayList<Course>();
		try {
			allCourses = courseDB.loadAllCourses();
		} catch (Exception e) {
			logger.error("ERROR: Unable to assign instructor.");
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		model.addAttribute("courses", allCourses);
		return "admin/course";
	}

	@GetMapping("/admin/assigninstructor")
	public String assignInstructor(Model model, @RequestParam(name = ID) long courseID) {
		ICoursePersistence courseDB = courseDependencyFactory.getCoursePersistence();
		Course c = new Course();
		try {
			courseDB.loadCourseByID(courseID, c);
			model.addAttribute("course", c);
			ICourseUserRelationshipPersistence courseUserRelationshipDB = courseDependencyFactory
					.getCourseUserRelationshipPersistence();
			List<User> allUsersNotCurrentlyInstructors = courseUserRelationshipDB
					.findAllUsersWithoutCourseRole(Role.INSTRUCTOR, courseID);
			model.addAttribute("users", allUsersNotCurrentlyInstructors);
		} catch (RuntimeSQLException e) {
			logger.error("ERROR: Unable to assign instructor.");
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		return "admin/assigninstructor";
	}

	@GetMapping("/admin/deletecourse")
	public ModelAndView deleteCourse(@RequestParam(name = ID) long courseID, Model model) {
		try {
			ICoursePersistence courseDB = courseDependencyFactory.getCoursePersistence();
			Course c = new Course();
			c.setId(courseID);
			c.delete(courseDB);
		} catch (RuntimeSQLException e) {
			logger.error("ERROR: Unable to assign instructor.");
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		ModelAndView mav = new ModelAndView("redirect:/admin/course");
		return mav;
	}

	@RequestMapping(value = "/admin/createcourse", method = RequestMethod.POST)
	public ModelAndView createCourse(@RequestParam(name = TITLE) String title, Model model) {
		try {
			ICoursePersistence courseDB = courseDependencyFactory.getCoursePersistence();
			Course c = new Course();
			c.setTitle(title);
			c.createCourse(courseDB);
		} catch (RuntimeSQLException e) {
			logger.error("ERROR: Unable to assign instructor.");
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		ModelAndView mav = new ModelAndView("redirect:/admin/course");
		return mav;
	}

	@RequestMapping(value = "/admin/assigninstructor", method = RequestMethod.POST)
	public ModelAndView assignInstructorToCourse(@RequestParam(name = INSTRUCTOR) List<Integer> instructor,
			@RequestParam(name = ID) long courseID, Model model) {
		Course c = new Course();
		c.setId(courseID);
		Iterator<Integer> iter = instructor.iterator();
		try {
			System.out.println("HEREEREREEEEE");
			ICourseUserRelationshipPersistence courseUserRelationshipDB = courseDependencyFactory
					.getCourseUserRelationshipPersistence();
			while (iter.hasNext()) {
				User u = new User();
				u.setId(iter.next().longValue());
				courseUserRelationshipDB.enrollUser(c, u, Role.INSTRUCTOR);
			}
		} catch (RuntimeSQLException e) {
			logger.error("ERROR: Unable to assign instructor.");
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		ModelAndView mav = new ModelAndView("redirect:/admin/course");
		return mav;
	}

}