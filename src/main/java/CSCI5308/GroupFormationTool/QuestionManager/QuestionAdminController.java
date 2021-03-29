package CSCI5308.GroupFormationTool.QuestionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;

@Controller
public class QuestionAdminController {
	private final Logger logger = LoggerFactory.getLogger(QuestionAdminController.class);
	private static final String ID = "id";
	private static final String BannerID = "bannerID";
	private final IQuestionPersistence questionDB;

	public QuestionAdminController(IGlobalFactoryProvider provider) {
		questionDB = provider.getQuestionDependencyFactory().getQuestionPersistence();
	}

	@RequestMapping("/question/delete")
	public ModelAndView deleteQuestion(Model model, @RequestParam(name = ID) long questionId,
			@RequestParam(name = BannerID) String bannerId) {
		questionDB.deleteQuestionByQuestionId(questionId);
		logger.info("Request by " + bannerId + " to delete question with id - " + questionId);
		ModelAndView mav = new ModelAndView("redirect:/question/questionmanager/title?bannerID=" + bannerId);
		return mav;
	}

	@RequestMapping("/question/add")
	public String addQuestion(Model model) {
		List<QuestionType> questionType = new ArrayList<QuestionType>();
		questionType = Arrays.asList(QuestionType.values());
		model.addAttribute("questionTypes", questionType);
		return "/question/addquestion";
	}

	@RequestMapping("/question/reviewQuestion")
	public ModelAndView addOptions(Model model, @RequestParam(name = BannerID) String bannerId,
			@RequestParam("qtitle") String titleString, @RequestParam("qtext") String textString,
			@RequestParam("qtype") String type) {

		Question question = Question.getBuilder().setText(textString).setTitle(titleString)
				.setType(QuestionType.valueOf(type)).buildQuestion();
		Options options = new Options();
		options.addOption();
		ModelAndView mav = new ModelAndView();
		mav.addObject("question", question);
		mav.addObject("options", options);
		mav.setViewName("/question/reviewquestion");
		return mav;
	}

	@RequestMapping("/question/submit")
	public ModelAndView saveQuestion(Model model, @RequestParam("qtitle") String titleString,
			@RequestParam("qtext") String textString, @RequestParam("qtype") String type,
			@ModelAttribute Options options, @RequestParam(name = BannerID) String bannerId) {

		Question question = Question.getBuilder().setText(textString).setTitle(titleString)
				.setType(QuestionType.valueOf(type.toUpperCase())).buildQuestion();

		try {
			long questionID = question.createQuestion(questionDB, bannerId);
			options.saveOptions(questionDB, questionID);
		} catch (RuntimeSQLException re) {
			logger.error("SQL Exception: " + re.getCause().getMessage());
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		ModelAndView mav = new ModelAndView("redirect:/question/questionmanager/title?bannerID=" + bannerId);
		return mav;
	}

	@RequestMapping(value = "/question/submit", params = { "addOptionRow" })
	public ModelAndView addOptionRow(@RequestParam("qtitle") String titleString,
			@RequestParam("qtext") String textString, @RequestParam("qtype") String type,
			@ModelAttribute Options options, final BindingResult bindingResult) {
		logger.info("Adding new option for Question " + titleString + " of type " + type);
		Question question = new Question.QuestionBuild().setText(textString).setTitle(titleString)
				.setType(QuestionType.valueOf(type.toUpperCase())).buildQuestion();
		options.addOption();
		ModelAndView mav = new ModelAndView();
		mav.addObject("question", question);
		mav.addObject("options", options);
		mav.setViewName("/question/reviewquestion");
		return mav;
	}

}
