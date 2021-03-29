package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.QuestionManager.Question;

@Controller
public class SurveyManagerController {

	private final Logger logger = LoggerFactory.getLogger(SurveyManagerController.class);
	private static final String BannerID = "bannerID";
	private final ISurveyPersistence surveyDB;
	private final ISurveyQuestionPersistence surveyQuestionDB;

	public SurveyManagerController(IGlobalFactoryProvider provider) {
		surveyDB = provider.getSurveyDependencyFactory().getSurveyPersistence();
		surveyQuestionDB = provider.getSurveyDependencyFactory().getSurveyQuestionPersistence();
	}

	@RequestMapping("/survey/createsurvey/{courseID}")
	public String createSurvey(Model model, @RequestParam(name = BannerID) String bannerID,
			@PathVariable long courseID) {
		model.addAttribute("courseId", courseID);
		Survey survey = Survey.getSurveyBuilder().setUserBannerID(bannerID).buildSurvey();
		try {
			survey = surveyDB.getSurveyByCourse(courseID);
			if (survey != null && survey.getStatus() == SurveyStatus.DRAFT) {
				Long surveyID = survey.getSurveyId();
				System.out.println("Survey ID: " + surveyID);
				SurveyQuestion surveyQuestion = new SurveyQuestion();
				List<Question> questionBank = surveyQuestion.getQuestionBank(surveyQuestionDB, bannerID);
				List<Long> questionsInSurvey = new ArrayList<Long>();
				System.out.println(
						"DB List: " + surveyID + surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, surveyID));
				if (surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, surveyID) != null) {
					questionsInSurvey.addAll(surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, surveyID));
				}
				surveyQuestion.setQuestionList(questionsInSurvey);
				model.addAttribute("questionBank", questionBank);
				model.addAttribute("surveyTitle", survey.getSurveyTitle());
				model.addAttribute("bannerID", survey.getUserBannerId());
				model.addAttribute("surveyID", survey.getSurveyId());
				model.addAttribute("survey", survey);
				model.addAttribute("surveyQuestion", surveyQuestion);
				return "/survey/addquestions";
			}
			if (survey != null && survey.getStatus() == SurveyStatus.PUBLISHED) {
				List<Long> questionsInSurvey = new ArrayList<Long>();
				SurveyQuestion surveyQuestion = new SurveyQuestion();
				questionsInSurvey = surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, survey.getSurveyId());
				surveyQuestion.setQuestionList(questionsInSurvey);
				model.addAttribute("surveyID", survey.getSurveyId());

				model.addAttribute("bannerID", bannerID);
				model.addAttribute("courseID", courseID);
				model.addAttribute("surveyTitle", survey.getSurveyTitle());
				model.addAttribute("surveyQuestion", surveyQuestion);
				return "/survey/viewsurvey";
			}
		} catch (RuntimeSQLException e) {
			logger.info("Error Connecting to the Database");
			model.addAttribute("error", "Database Connection Seems To Be Weak");
		}

		return "survey/createsurvey";
	}

	@RequestMapping("/survey/addquestion")
	public String addQuestions(Model model, @RequestParam("surveyTitle") String surveyTitle,
			@RequestParam("bannerID") String bannerID, @RequestParam("courseID") long courseID,
			@RequestParam(required = false, value = "surveyID") String surveyId,
			@RequestParam(required = false, name = "group-size") Long groupsize) {
		SurveyQuestion surveyQuestion = new SurveyQuestion();
		List<Question> questionBank = surveyQuestion.getQuestionBank(surveyQuestionDB, bannerID);
		List<Long> questionsInSurvey = new ArrayList<Long>();
		Survey survey = Survey.getSurveyBuilder().setSurveyTitle(surveyTitle).setUserBannerID(bannerID)
				.setSurveyStatus(SurveyStatus.DRAFT).buildSurvey();
		try {
			if (survey.getSurveyByCourse(surveyDB, courseID) == null) {
				Long surveyID = survey.createSurvey(surveyDB, courseID);
				logger.info("Fetched Survey with ID: " + surveyID);
				if (surveyID == -1) {
					logger.error("ERROR: Unable to create survey - no such course exists");
					model.addAttribute("message", "Unable to create survey - no such course exists");
					return "error";
				}
			}
			Long surveyID = survey.getSurveyByCourse(surveyDB, courseID).getSurveyId();
			System.out
					.println("DB List: " + surveyID + surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, surveyID));
			if (surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, surveyID) != null) {
				questionsInSurvey.addAll(surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, surveyID));
			}
			surveyQuestion.setQuestionList(questionsInSurvey);
			System.out.println("HERE" + groupsize);
			if (groupsize != null) {
				model.addAttribute("groupsize", groupsize);
			}

			model.addAttribute("surveyID", surveyID);
			model.addAttribute("bannerID", bannerID);
			model.addAttribute("courseID", courseID);
			model.addAttribute("surveyTitle", surveyTitle);
			model.addAttribute("questionBank", questionBank);
			model.addAttribute("surveyQuestion", surveyQuestion);
		} catch (RuntimeSQLException e) {
			logger.info("Error Connecting to the Database");
			model.addAttribute("error", "Database Connection Seems To Be Weak");
		}
		return "survey/addquestions";
	}

	@RequestMapping(value = "/survey/addquestion", params = { "addSurveyQuestion" })
	public String addQuestionsToSurvey(Model model, @RequestParam("surveyTitle") String surveyTitle,
			@RequestParam(name = BannerID) String bannerID, @RequestParam("surveyID") String surveyId,
			@RequestParam("courseID") long courseID,
			@RequestParam(name = "questionid", required = false) List<Long> questionIdList,
			@RequestParam(required = false, name = "group-size") Long groupsize) {
		Long surveyID = Long.parseLong(surveyId);
		SurveyQuestion surveyQuestion = new SurveyQuestion();
		try {

			List<Question> questionBank = surveyQuestion.getQuestionBank(surveyQuestionDB, bannerID);
			System.out
					.println("DB List: " + surveyID + surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, surveyID));
			if (surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, surveyID) != null) {
				questionIdList.addAll(surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, surveyID));
			}
			System.out.println("EERE" + groupsize);
			if (groupsize != null) {
				model.addAttribute("groupsize", groupsize);
			}
			surveyQuestion.setQuestionList(questionIdList);
			model.addAttribute("surveyID", surveyID);
			model.addAttribute("bannerID", bannerID);
			model.addAttribute("courseID", courseID);
			model.addAttribute("surveyID", surveyID);
			model.addAttribute("surveyTitle", surveyTitle);
			model.addAttribute("questionBank", questionBank);
			model.addAttribute("surveyQuestion", surveyQuestion);
		} catch (RuntimeSQLException e) {
			logger.info("Error Connecting to the Database");
			model.addAttribute("error", "Database Connection Seems To Be Weak");
		}
		return "survey/addquestions";
	}
}
