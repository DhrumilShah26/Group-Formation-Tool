package CSCI5308.GroupFormationTool.QuestionManager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//wrapper class to pass lists from thymeleaf to controllers
public class Options {
	private final Logger logger = LoggerFactory.getLogger(Options.class);
	public List<OptionValue> optionList;

	public Options() {
		setDefault();
	}

	public void setDefault() {
		optionList = new ArrayList<OptionValue>();
	}

	public List<OptionValue> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<OptionValue> optionList) {
		this.optionList = optionList;
	}

	public void addOption() {
		String index = String.valueOf(optionList.size() + 1);
		optionList.add(new OptionValue(index, index));
	}

	public void saveOptions(IQuestionPersistence questionDB, long questionID) {
		int order = 1;
		if (questionID != -1) {
			for (OptionValue option : optionList) {
				String text = option.getText();
				String storedAs = option.getStoredAs();
				if (isStringEmpty(text) || isStringEmpty(storedAs)) {
					continue;
				}
				logger.info("Saving options for question with ID - " + questionID);
				questionDB.createQuestionOption(option, order++, questionID);
			}
		}

	}

	public boolean isStringEmpty(String s) {
		logger.info("Checking if String " + s + " is empty");
		return s.replaceAll(" ", "").length() == 0;
	}

}
