package CSCI5308.GroupFormationTool.QuestionManager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;

@Controller
public class QuestionManagerController {
	private final Logger logger = LoggerFactory.getLogger(QuestionManagerController.class);
	private static final String BannerID = "bannerID";
	private final IQuestionPersistence questionDB;

	public QuestionManagerController(IGlobalFactoryProvider provider) {
		questionDB = provider.getQuestionDependencyFactory().getQuestionPersistence();
	}

	@RequestMapping("/question/questionmanager/title")
	public String questionsByTitle(Model model, @RequestParam(name = BannerID) String bannerID) {

		try {
			List<Question> questionList = questionDB.loadQuestionsSortedByTitle(bannerID);
			model.addAttribute("questionList", questionList);
		} catch (RuntimeSQLException re) {
			logger.error("SQL Exception: " + re.getCause().getMessage());
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		return "question/questions";
	}

	@RequestMapping("/question/questionmanager/date")
	public String questionsByDate(Model model, @RequestParam(name = BannerID) String bannerID) {

		try {
			List<Question> questionList = questionDB.loadSortedQuestionsSortedByDate(bannerID);
			model.addAttribute("questionList", questionList);
		} catch (RuntimeSQLException re) {
			logger.error("SQL Exception: " + re.getCause().getMessage());
			model.addAttribute("error", "Database Connection Seems Weak");
		}
		return "question/questions";
	}

}
