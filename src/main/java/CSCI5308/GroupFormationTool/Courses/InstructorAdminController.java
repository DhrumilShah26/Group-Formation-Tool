package CSCI5308.GroupFormationTool.Courses;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;

@Controller
public class InstructorAdminController {

	private final Logger logger = LoggerFactory.getLogger(CourseAdminController.class);
	final AbstractCourseDependencyFactory courseDependencyFactory;
	final AbstractAclDependencyFactory aclDependencyFactory;

	private static final String ID = "id";
	private static final String FILE = "file";
	private static final String SUCCESSFUL = "successful";
	private static final String FAILURES = "failures";
	private static final String DISPLAY_RESULTS = "displayresults";

	public InstructorAdminController(IGlobalFactoryProvider provider) {
		this.courseDependencyFactory = provider.getCourseDependencyFactory();
		this.aclDependencyFactory = provider.getAclDependencyFactory();
	}

	@GetMapping("/course/instructoradmin")
	public String instructorAdmin(Model model, @RequestParam(name = ID) long courseID) {
		ICoursePersistence courseDB = courseDependencyFactory.getCoursePersistence();
		Course course = new Course();
		try {
			courseDB.loadCourseByID(courseID, course);
			model.addAttribute("course", course);
			model.addAttribute("displayresults", false);
		} catch (RuntimeSQLException e) {
			logger.error("ERROR: Unable to assign instructor.");
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		if (course.isCurrentUserEnrolledAsRoleInCourse(aclDependencyFactory.getCurrentUser(), Role.INSTRUCTOR,
				courseDependencyFactory.getCourseUserRelationship())
				|| course.isCurrentUserEnrolledAsRoleInCourse(aclDependencyFactory.getCurrentUser(), Role.TA,
						courseDependencyFactory.getCourseUserRelationship())) {
			return "course/instructoradmin";
		} else {
			return "logout";
		}
	}

	@GetMapping("/course/instructoradminresults")
	public String instructorAdmin(Model model, @RequestParam(name = ID) long courseID,
			@RequestParam(name = SUCCESSFUL, required = false) List<String> successful,
			@RequestParam(name = FAILURES, required = false) List<String> failures,
			@RequestParam(name = DISPLAY_RESULTS) boolean displayResults) {
		ICoursePersistence courseDB = courseDependencyFactory.getCoursePersistence();
		Course course = new Course();
		try {
			courseDB.loadCourseByID(courseID, course);
			model.addAttribute("course", course);
			model.addAttribute("displayresults", false);
			model.addAttribute(SUCCESSFUL, successful);
			model.addAttribute(FAILURES, failures);
			model.addAttribute(DISPLAY_RESULTS, displayResults);
			if (course.isCurrentUserEnrolledAsRoleInCourse(aclDependencyFactory.getCurrentUser(), Role.INSTRUCTOR,
					courseDependencyFactory.getCourseUserRelationship())
					|| course.isCurrentUserEnrolledAsRoleInCourse(aclDependencyFactory.getCurrentUser(), Role.TA,
							courseDependencyFactory.getCourseUserRelationship())) {
				return "course/instructoradmin";
			} else {
				return "logout";
			}
		} catch (RuntimeSQLException e) {
			logger.error("ERROR: Unable to assign instructor.");
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		return "course/instructoradmin";
	}

	@GetMapping("/course/enrollta")
	public String enrollTA(Model model, @RequestParam(name = ID) long courseID) {
		ICoursePersistence courseDB = courseDependencyFactory.getCoursePersistence();
		Course course = new Course();
		try {
			courseDB.loadCourseByID(courseID, course);
		} catch (RuntimeSQLException e) {
			logger.error("ERROR: Unable to assign instructor.");
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		model.addAttribute("course", course);
		if (course.isCurrentUserEnrolledAsRoleInCourse(aclDependencyFactory.getCurrentUser(), Role.INSTRUCTOR,
				courseDependencyFactory.getCourseUserRelationship())
				|| course.isCurrentUserEnrolledAsRoleInCourse(aclDependencyFactory.getCurrentUser(), Role.TA,
						courseDependencyFactory.getCourseUserRelationship())) {
			return "course/enrollta";
		} else {
			return "logout";
		}
	}

	@RequestMapping(value = "/course/uploadcsv", consumes = { "multipart/form-data" })
	public ModelAndView upload(@RequestParam(name = FILE) MultipartFile file, @RequestParam(name = ID) long courseID,
			Model model) {
		ICoursePersistence courseDB = courseDependencyFactory.getCoursePersistence();
		IStudentCsvImport importer = courseDependencyFactory.getStudentCsvImport();
		Course course = new Course();
		try {
			courseDB.loadCourseByID(courseID, course);
			importer.enrollStudentFromRecord(course, file, courseDependencyFactory, aclDependencyFactory);
		} catch (RuntimeSQLException e) {
			logger.error("ERROR: Unable to assign instructor.");
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		ModelAndView mav = new ModelAndView("redirect:/course/instructoradminresults?id=" + Long.toString(courseID));
		mav.addObject("successful", importer.getSuccessResults());
		mav.addObject("failures", importer.getFailureResults());
		mav.addObject("displayresults", true);

		return mav;
	}
}
