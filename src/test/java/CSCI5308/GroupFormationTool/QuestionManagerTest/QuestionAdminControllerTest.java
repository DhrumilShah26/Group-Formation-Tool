package CSCI5308.GroupFormationTool.QuestionManagerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.OptionValue;
import CSCI5308.GroupFormationTool.QuestionManager.Options;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionAdminController;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionAdminControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	IQuestionPersistence questionDB;

	QuestionAdminController controller = new QuestionAdminController(new GlobalDependencyMock());

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void deleteQuestion() throws Exception {
		String BannerID = "B-000000";
		String questionID = "1";

		mockMvc.perform(get("/question/delete").param("id", questionID).param("bannerID", BannerID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/question/questionmanager/title?bannerID=B-000000"));
	}

	@Test
	public void addQuestionTest() throws Exception {
		mockMvc.perform(get("/question/add")).andExpect(view().name("/question/addquestion"))
				.andExpect(status().isOk());
	}

	@Test
	public void addOptionsTest() throws Exception {
		String BannerID = "B-000000";
		String titleString = "title";
		String textString = "text";
		String type = "MCQONE";

		mockMvc.perform(get("/question/reviewQuestion").param("qtitle", titleString).param("qtext", textString)
				.param("qtype", type).param("bannerID", BannerID)).andExpect(status().isOk())
				.andExpect(view().name("/question/reviewquestion"));
	}

	@Test
	public void saveQuestionTest() throws Exception {
		String BannerID = "B-000000";
		String titleString = "title";
		String textString = "text";
		String type = "MCQONE";
		Options options = new Options();
		OptionValue optionValue = new OptionValue();
		optionValue.setStoredAs("1");
		optionValue.setText("Text 1");
		List<OptionValue> optionValuesList = new ArrayList<OptionValue>();
		optionValuesList.add(optionValue);
		options.setOptionList(optionValuesList);

		mockMvc.perform(get("/question/submit").param("qtitle", titleString).param("qtext", textString)
				.param("qtype", type).param("bannerID", BannerID).flashAttr("options", new Options()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/question/questionmanager/title?bannerID=" + BannerID));

		mockMvc.perform(get("/question/submit").param("qtitle", titleString).param("qtext", textString)
				.param("qtype", type).param("bannerID", "B-DB_CONNECTION_EX").flashAttr("options", options))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/question/questionmanager/title?bannerID=" + "B-DB_CONNECTION_EX"));
	}

	@Test
	public void addOptionRowTest() throws Exception {
		String BannerID = "B-000000";
		String titleString = "title";
		String textString = "text";
		String type = "MCQONE";

		mockMvc.perform(
				get("/question/submit?addOptionRow=true").param("qtitle", titleString).param("qtext", textString)
						.param("qtype", type).param("bannerID", BannerID).flashAttr("options", new Options()))
				.andExpect(status().isOk()).andExpect(view().name("/question/reviewquestion"));
	}

}
