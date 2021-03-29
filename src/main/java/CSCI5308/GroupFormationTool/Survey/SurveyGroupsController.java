package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;

@Controller
public class SurveyGroupsController {

	private final ISurveyPersistence surveyDB;
	private final AbstractGroupingStrategyFactory factory;

	public SurveyGroupsController(IGlobalFactoryProvider provider) {
		surveyDB = provider.getSurveyDependencyFactory().getSurveyPersistence();
		factory = new GroupingStrategyFactory();
	}

	@RequestMapping("survey/formgroups")
	public String formGroups(@RequestParam(required = false, value = "surveyID") String surveyID,
			@RequestParam(required = false, value = "courseID") Long courseID,
			@RequestParam(required = false, value = "groupsize") int groupsize,
			@RequestParam(required = false, value = "surveyTitle") String surveyTitle, Model model) {

		List<Long> studentList = new ArrayList<Long>();
		studentList.addAll(surveyDB.getStudentByCourse(courseID));
		System.out.println("survey Title" + studentList);

		List<SurveyResponse> responses = new ArrayList<>();

		for (Long userId : studentList) {
			SurveyResponse response = new SurveyResponse();

			response.setUserID(userId.intValue());
			responses.add(response);
		}
		AbstractGroupingStrategy groupingStrategy = factory.getDefaultStrategy();
		List<Group> groups = groupingStrategy.makeGroups(groupsize, responses);
		model.addAttribute("groups", groups);
		model.addAttribute("surveyID", surveyID);
		model.addAttribute("courseID", courseID);
		model.addAttribute("surveyTitle", surveyTitle);
		model.addAttribute("studentList", studentList);
		System.out.println(studentList.size());
		return "survey/formgroups";
	}
}
