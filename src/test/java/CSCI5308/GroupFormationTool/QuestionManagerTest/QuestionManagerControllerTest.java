package CSCI5308.GroupFormationTool.QuestionManagerTest;

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
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionManagerController;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionManagerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	IQuestionPersistence questionDB;

	QuestionManagerController controller = new QuestionManagerController(new GlobalDependencyMock());

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void questionsByTitleTest() throws Exception {
		String BannerID = "B-000000";

		mockMvc.perform(get("/question/questionmanager/title").param("bannerID", BannerID))
				.andExpect(view().name("question/questions")).andExpect(status().isOk());

		mockMvc.perform(get("/question/questionmanager/title").param("bannerID", "B-DB_CONNECTION_EX"))
				.andExpect(view().name("question/questions")).andExpect(status().isOk());
	}

	@Test
	public void questionsByTitleDate() throws Exception {
		String BannerID = "B-000000";

		mockMvc.perform(get("/question/questionmanager/date").param("bannerID", BannerID))
				.andExpect(view().name("question/questions")).andExpect(status().isOk());

		mockMvc.perform(get("/question/questionmanager/date").param("bannerID", "B-DB_CONNECTION_EX"))
				.andExpect(view().name("question/questions")).andExpect(status().isOk());
	}

}
