package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.QuestionManager.Question;

@Controller
public class SurveyAdminController {

	private final Logger logger = LoggerFactory.getLogger(SurveyAdminController.class);
	private static final String BannerID = "bannerID";
	private final ISurveyQuestionPersistence surveyQuestionDB;
	private final ISurveyPersistence surveyDB;

	public SurveyAdminController(IGlobalFactoryProvider provider) {
		surveyDB = provider.getSurveyDependencyFactory().getSurveyPersistence();
		surveyQuestionDB = provider.getSurveyDependencyFactory().getSurveyQuestionPersistence();
	}

	@RequestMapping(value = "/survey/savesurvey", params = "removeQuestion")
	public String removeQuestionsFromSurvey(Model model, @RequestParam("surveyTitle") String surveyTitle,
			@RequestParam(name = BannerID) String bannerID, @RequestParam("surveyID") String surveyId,
			@RequestParam("courseID") String courseID, @RequestParam("rmquestionid") List<Long> rmquestionId,
			@RequestParam("questionid") List<Long> questionIdList,
			@RequestParam(required = false, name = "group-size") Long groupsize,
			@RequestParam(required = false, name = "rulesimilarity") String rulesimilarity,
			@RequestParam(required = false, name = "reward") String reward) {
		Long surveyID = Long.parseLong(surveyId);
		SurveyQuestion surveyQuestion = new SurveyQuestion();
		List<Question> questionBank = new ArrayList<Question>();
		try {
			questionBank = surveyQuestion.getQuestionBank(surveyQuestionDB, bannerID);
		} catch (RuntimeSQLException e) {
			logger.info("Error Connecting to the Database");
			model.addAttribute("error", "Database Connection Seems To Be Weak");
		}
		logger.info("Existing list: " + questionIdList);
		logger.info("List of questions to remove: " + rmquestionId);
		if (questionIdList.equals(rmquestionId)) {
			surveyQuestion.setQuestionList(questionIdList);
			if (groupsize != null) {
				model.addAttribute("groupsize", groupsize);
			}
			if (reward != null) {
				model.addAttribute("reward", reward);
			}
			if (rulesimilarity != null) {
				model.addAttribute("rulesimilarity", rulesimilarity);
			}
			model.addAttribute("error", "Cannot Delete All Questions! Require Atleast 1 question");
			model.addAttribute("surveyID", surveyID);
			model.addAttribute("bannerID", bannerID);
			model.addAttribute("courseID", courseID);
			model.addAttribute("surveyID", surveyID);
			model.addAttribute("surveyTitle", surveyTitle);
			model.addAttribute("questionBank", questionBank);
			model.addAttribute("surveyQuestion", surveyQuestion);
			return "survey/addquestions";
		}
		if (groupsize != null) {
			model.addAttribute("groupsize", groupsize);
		}
		if (reward != null) {
			model.addAttribute("reward", reward);
		}
		if (rulesimilarity != null) {
			model.addAttribute("rulesimilarity", rulesimilarity);
		}
		questionIdList.removeAll(rmquestionId);
		logger.info("Updated list: " + questionIdList);
		surveyQuestion.setQuestionList(questionIdList);
		model.addAttribute("surveyID", surveyID);
		model.addAttribute("bannerID", bannerID);
		model.addAttribute("courseID", courseID);
		model.addAttribute("surveyID", surveyID);
		model.addAttribute("surveyTitle", surveyTitle);
		model.addAttribute("questionBank", questionBank);
		model.addAttribute("surveyQuestion", surveyQuestion);
		return "survey/addquestions";
	}

	@RequestMapping(value = "/survey/savesurvey", params = "saveAsDraft")
	public String saveSurveyDraft(Model model, @RequestParam("surveyTitle") String surveyTitle,
			@RequestParam(name = BannerID) String bannerID, @RequestParam("surveyID") String surveyId,
			@RequestParam("courseID") String courseID, @RequestParam("questionid") List<Long> questionIdList,
			@RequestParam(required = false, name = "group-size") Long groupsize,
			@RequestParam(required = false, name = "rulesimilarity") String rulesimilarity,
			@RequestParam(required = false, name = "reward") String reward) {
		System.out.println("Group Size" + groupsize);
		SurveyQuestion surveyQuestion = new SurveyQuestion();
		List<Question> questionBank = new ArrayList<Question>();
		try {
			questionBank = surveyQuestion.getQuestionBank(surveyQuestionDB, bannerID);
			logger.info("Current list to save: " + questionIdList);
			surveyQuestion.setQuestionList(questionIdList);
			surveyQuestion.saveQuestionsDraft(surveyQuestionDB, questionIdList, Long.parseLong(surveyId));
			surveyQuestion
					.setQuestionList(surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, Long.parseLong(surveyId)));
		} catch (RuntimeSQLException e) {
			logger.info("Error Connecting to the Database");
			model.addAttribute("error", "Database Connection Seems To Be Weak");
		}
		if (groupsize != null) {
			model.addAttribute("groupsize", groupsize);
		}
		if (reward != null) {
			model.addAttribute("reward", reward);
		}
		if (rulesimilarity != null) {
			model.addAttribute("rulesimilarity", rulesimilarity);
		}
		model.addAttribute("surveyID", Long.parseLong(surveyId));
		model.addAttribute("bannerID", bannerID);
		model.addAttribute("courseID", courseID);
		model.addAttribute("surveyTitle", surveyTitle);
		model.addAttribute("questionBank", questionBank);
		model.addAttribute("surveyQuestion", surveyQuestion);
		return "survey/addquestions";
	}

	@RequestMapping(value = "/survey/savesurvey", params = "publishSurvey")
	public String publishSurvey(Model model, @RequestParam("surveyTitle") String surveyTitle,
			@RequestParam(name = BannerID) String bannerID, @RequestParam("courseID") long courseID,
			@RequestParam("questionid") List<Long> questionIdList,
			@RequestParam(required = false, name = "group-size") int groupsize,
			@RequestParam(required = false, name = "rulesimilarity") String rulesimilarity,
			@RequestParam(required = false, name = "reward") String reward) {
		SurveyQuestion surveyQuestion = new SurveyQuestion();
		logger.info("Current list to publish: " + questionIdList);
		Survey survey = Survey.getSurveyBuilder().setUserBannerID(bannerID).buildSurvey();
		Boolean published = false;
		try {
			survey = survey.getSurveyByCourse(surveyDB, courseID);
			Long surveyID = survey.getSurveyId();
			System.out.println("Survey ID: " + surveyID);
			surveyQuestion.setQuestionList(questionIdList);
			surveyQuestion.saveQuestionsDraft(surveyQuestionDB, questionIdList, surveyID);
			surveyQuestion.setQuestionList(surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, surveyID));
			survey = survey.getSurveyByCourse(surveyDB, courseID);
			published = survey.updateSurveyStatus(surveyDB, SurveyStatus.PUBLISHED.toString(), surveyID);
			if (groupsize != 0) {
				model.addAttribute("groupsize", groupsize);
			}
			if (reward != null) {
				model.addAttribute("reward", reward);
			}
			if (rulesimilarity != null) {
				model.addAttribute("rulesimilarity", rulesimilarity);
			}
			model.addAttribute("surveyID", surveyID);
			model.addAttribute("bannerID", bannerID);
			model.addAttribute("courseID", courseID);
			model.addAttribute("surveyTitle", surveyTitle);
			model.addAttribute("surveyQuestion", surveyQuestion);
		} catch (RuntimeSQLException e) {
			e.printStackTrace();
			logger.info("Error Connecting to the Database");
			model.addAttribute("error", "Database Connection Seems To Be Weak");
			model.addAttribute("surveyQuestion", surveyQuestion);
		}
		if (published) {
			return "survey/viewsurvey";
		}
		model.addAttribute("errorStatus", "Unable to update Survey Status");
		return "survey/addquestions";
	}

}
