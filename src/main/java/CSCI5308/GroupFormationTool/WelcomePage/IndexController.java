package CSCI5308.GroupFormationTool.WelcomePage;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import CSCI5308.GroupFormationTool.Courses.*;

@Controller
public class IndexController
{
	final
	AbstractCourseDependencyFactory courseDependencyFactory;

	public IndexController(IGlobalFactoryProvider globalFactoryProvider)
	{
		this.courseDependencyFactory = globalFactoryProvider.getCourseDependencyFactory();
	}

	@GetMapping("/")
	public String greeting(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.isAuthenticated())
		{
			ICoursePersistence courseDB = courseDependencyFactory.getCoursePersistence();
			List<Course> allCourses = courseDB.loadAllCourses();
			model.addAttribute("courses", allCourses);
		}
		return "index";
	}
}