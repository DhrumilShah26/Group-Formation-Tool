package CSCI5308.GroupFormationTool.SurveyTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.Survey.ISurveyPersistence;
import CSCI5308.GroupFormationTool.Survey.ISurveyQuestionPersistence;
import CSCI5308.GroupFormationTool.Survey.Survey;
import CSCI5308.GroupFormationTool.Survey.SurveyManagerController;

@SpringBootTest
@AutoConfigureMockMvc
public class SurveyManagerControllerTest {

	@Mock
	Survey survey;

	@Mock
	ISurveyPersistence surveyDB;

	@Mock
	ISurveyQuestionPersistence surveyQuestionDB;

	@Autowired
	private MockMvc mockMvc;

	SurveyManagerController controller = new SurveyManagerController(new GlobalDependencyMock());

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void createSurvey() throws Exception {

		mockMvc.perform(get("/survey/createsurvey/{courseID}", "9999").param("bannerID", "B00851387"))
				.andExpect(status().isOk()).andExpect(view().name("/survey/addquestions"));

		mockMvc.perform(get("/survey/createsurvey/{courseID}", "9009").param("bannerID", "B00851387"))
				.andExpect(status().isOk()).andExpect(view().name("survey/createsurvey"));

		mockMvc.perform(get("/survey/createsurvey/{courseID}", "9900").param("bannerID", "B00851387"))
				.andExpect(status().isOk()).andExpect(view().name("/survey/viewsurvey"));

	}

	@Test
	public void addQuestions() throws Exception {

		mockMvc.perform(get("/survey/addquestion").param("bannerID", "B00851387").param("surveyTitle", "Title")
				.param("courseID", "-1")).andExpect(status().isOk()).andExpect(view().name("error"));

		mockMvc.perform(get("/survey/addquestion").param("bannerID", "B00851387").param("surveyTitle", "Title")
				.param("courseID", "9999")).andExpect(status().isOk()).andExpect(view().name("survey/addquestions"));

	}

	@Test
	public void addQuestionsToSurvey() throws Exception {

		List<Long> questionIdList = new ArrayList<Long>();
		questionIdList = null;
		mockMvc.perform(get("/survey/addquestion").param("addSurveyQuestion", "true").param("surveyTitle", "Title")
				.param("bannerID", "B00851387").param("surveyID", "999999").param("courseID", "9999999")
				.param("questionid", "1").param("questionid", "2")).andExpect(status().isOk())
				.andExpect(model().attribute("questionid", questionIdList))
				.andExpect(view().name("survey/addquestions"));
	}

}
