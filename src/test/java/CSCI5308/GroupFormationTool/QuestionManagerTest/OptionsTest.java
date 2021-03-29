package CSCI5308.GroupFormationTool.QuestionManagerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestionPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.OptionValue;
import CSCI5308.GroupFormationTool.QuestionManager.Options;

class OptionsTest {
	@Test
	public void ConstructorTests() {
		Options options = new Options();
		assertEquals(options.getOptionList().size(), 0);
	}

	@Test
	public void getOptionList() {
		Options options = new Options();
		List<OptionValue> list = new ArrayList<OptionValue>();
		list.add(new OptionValue("test", "test"));
		options.setOptionList(list);
		assertEquals(options.getOptionList(), list);

	}

	@Test
	public void setOptionList() {
		Options options = new Options();
		List<OptionValue> list = new ArrayList<OptionValue>();
		list.add(new OptionValue("test", "test"));
		options.setOptionList(list);
		assertEquals(options.getOptionList(), list);
	}

	@Test
	public void addOption() {
		Options options = new Options();
		options.addOption();
		assertFalse(options.getOptionList().isEmpty());
	}

	@Test
	public void saveOptions() {
		Options options = new Options();
		OptionValue option = new OptionValue("Test Text", "1");
		options.optionList.add(option);
		options.optionList.add(new OptionValue(" ", ""));
		options.optionList.add(new OptionValue("Text + No Stored As", ""));
		options.optionList.add(new OptionValue("", "2"));
		IQuestionPersistence questionDB = new QuestionDBMock();
		options.saveOptions(questionDB, 1);
		options.saveOptions(questionDB, -1);
		boolean status = questionDB.createQuestionOption(option, 1, 1);
		assertTrue(status == true);
		status = questionDB.createQuestionOption(option, 1, -1);
		assertFalse(status);
		option = new OptionValue("", "");
		status = questionDB.createQuestionOption(option, 1, 1);
		assertFalse(status);
	}

	@Test
	public void isStringEmpty() {
		Options options = new Options();
		String emptyString = " ";
		String notEmptyString = " abc";
		assertTrue(options.isStringEmpty(emptyString.replaceAll(" ", "")));
		assertFalse(options.isStringEmpty(notEmptyString.replaceAll(" ", "")));
	}

}
