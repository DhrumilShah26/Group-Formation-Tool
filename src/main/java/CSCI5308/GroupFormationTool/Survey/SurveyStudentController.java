package CSCI5308.GroupFormationTool.Survey;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;

@Controller
public class SurveyStudentController {
	
	private final ISurveyPersistence surveyDB;
	
	public SurveyStudentController(IGlobalFactoryProvider provider) {
		
		surveyDB = provider.getSurveyDependencyFactory().getSurveyPersistence();
	}
	
	@GetMapping("/survey/viewsurvey")
	public String getSurveybyCourse(@RequestParam("courseID") String courseID, Model model) {
		
		Long cID = Long.parseLong(courseID);
		System.out.println(cID);
		List<QuestionDetails> questionList = surveyDB.getSurveyforStudent(cID);
		List<QuestionOption> questionOptions = surveyDB.getQuestionOptionforStudent(cID);
		
		model.addAttribute("questionList", questionList);
		model.addAttribute("questionOptions", questionOptions);
		return "survey/viewsurveystudent";
		
	}
	
	@PostMapping("/survey/viewsurvey")
	public String submitSurvey(Model model) {
		
		model.addAttribute("questionList", "null");
		model.addAttribute("questionOptions", "null");
		return "survey/submitsurvey";
		
	}

}
