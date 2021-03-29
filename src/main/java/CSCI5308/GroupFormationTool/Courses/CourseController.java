package CSCI5308.GroupFormationTool.Courses;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;

@Controller
public class CourseController {

	private final Logger logger = LoggerFactory.getLogger(CourseController.class);
	final AbstractCourseDependencyFactory courseDependencyFactory;
	final AbstractAclDependencyFactory aclDependencyFactory;

	private static final String ID = "id";

	public CourseController(IGlobalFactoryProvider globalFactoryProvider) {
		this.courseDependencyFactory = globalFactoryProvider.getCourseDependencyFactory();
		this.aclDependencyFactory = globalFactoryProvider.getAclDependencyFactory();
	}

	@GetMapping("/course/course")
	public String course(Model model, @RequestParam(name = ID) long courseID) {
		List<Role> userRoles = new ArrayList<Role>();

		try {
			ICoursePersistence courseDB = courseDependencyFactory.getCoursePersistence();
			Course course = new Course();
			courseDB.loadCourseByID(courseID, course);
			model.addAttribute("course", course);
			userRoles = course.getAllRolesForCurrentUserInCourse(aclDependencyFactory.getCurrentUser(),
					courseDependencyFactory.getCourseUserRelationship());
		} catch (RuntimeSQLException e) {
			logger.error("ERROR: Unable to assign instructor.");
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		if (null == userRoles) {
			// Default is user is a guest.
			model.addAttribute("instructor", false);
			model.addAttribute("ta", false);
			model.addAttribute("student", false);
			model.addAttribute("guest", true);
		} else {
			model.addAttribute("instructor", userRoles.contains(Role.INSTRUCTOR));
			model.addAttribute("ta", userRoles.contains(Role.TA));
			model.addAttribute("student", userRoles.contains(Role.STUDENT));
			model.addAttribute("guest", userRoles.isEmpty());
		}
		return "course/course";
	}
}
