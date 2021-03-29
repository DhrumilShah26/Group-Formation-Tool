package CSCI5308.GroupFormationTool.SurveyTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import CSCI5308.GroupFormationTool.Survey.SurveyAdminController;
import CSCI5308.GroupFormationTool.Survey.SurveyQuestion;

@SpringBootTest
@AutoConfigureMockMvc
public class SurveyAdminControllerTest {

	@Mock
	Survey survey;

	@Mock
	SurveyQuestion surveyQuestion;

	@Mock
	ISurveyPersistence surveyDB;

	@Mock
	ISurveyQuestionPersistence surveyQuestionDB;

	@Autowired
	private MockMvc mockMvc;

	SurveyAdminController controller = new SurveyAdminController(new GlobalDependencyMock());

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void removeQuestionsFromSurvey() throws Exception {

		mockMvc.perform(get("/survey/savesurvey").param("removeQuestion", "true").param("bannerID", "B00851387")
				.param("surveyTitle", "Title").param("courseID", "9999").param("surveyID", "9999")
				.param("questionid", "1").param("questionid", "2").param("questionid", "3").param("rmquestionid", "1")
				.param("rmquestionid", "2")).andExpect(status().isOk()).andExpect(view().name("survey/addquestions"));

		mockMvc.perform(get("/survey/savesurvey").param("removeQuestion", "true").param("bannerID", "B00851387")
				.param("surveyTitle", "Title").param("courseID", "9999").param("surveyID", "9999")
				.param("questionid", "1").param("questionid", "2").param("rmquestionid", "1")
				.param("rmquestionid", "2")).andExpect(status().isOk()).andExpect(view().name("survey/addquestions"));

	}

	@Test
	public void saveSurveyDraft() throws Exception {

		mockMvc.perform(get("/survey/savesurvey").param("saveAsDraft", "true").param("bannerID", "B00851387")
				.param("surveyTitle", "Title").param("courseID", "9999").param("surveyID", "9999")
				.param("questionid", "1").param("questionid", "2").param("questionid", "3")).andExpect(status().isOk())
				.andExpect(view().name("survey/addquestions"));

	}

	@Test
	public void publishSurvey() throws Exception {

		mockMvc.perform(get("/survey/savesurvey").param("publishSurvey", "true").param("bannerID", "B00851387")
				.param("surveyTitle", "Title").param("courseID", "9999").param("questionid", "1")
				.param("questionid", "2").param("questionid", "3")).andExpect(status().isOk())
				.andExpect(view().name("survey/viewsurvey"));

		mockMvc.perform(get("/survey/savesurvey").param("publishSurvey", "true").param("bannerID", "B00851387")
				.param("surveyTitle", "Title").param("courseID", "9099").param("questionid", "1")
				.param("questionid", "2").param("questionid", "3")).andExpect(status().isOk())
				.andExpect(view().name("survey/addquestions"));

	}

}
