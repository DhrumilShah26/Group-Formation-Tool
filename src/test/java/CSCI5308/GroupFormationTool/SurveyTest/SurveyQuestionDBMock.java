package CSCI5308.GroupFormationTool.SurveyTest;

import java.util.ArrayList;
import java.util.List;

import CSCI5308.GroupFormationTool.QuestionManager.Question;
import CSCI5308.GroupFormationTool.Survey.ISurveyQuestionPersistence;

public class SurveyQuestionDBMock implements ISurveyQuestionPersistence {

	@Override
	public List<Question> getQuestionBank(String bannerID) {
		List<Question> questionBank = new ArrayList<Question>();
		questionBank.add(Question.getBuilder().setId(1).setTitle("Question 1").buildQuestion());
		questionBank.add(Question.getBuilder().setId(2).setTitle("Question 2").buildQuestion());
		if (bannerID != null && bannerID != "") {
			return questionBank;
		}
		return null;
	}

	@Override
	public boolean addQuestionsToSurvey(List<Long> questionIdList, Long surveyId) {
		if (surveyId > 0 && questionIdList != null && questionIdList.size() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Long> fetchQuestionBySurvey(Long surveyId) {
		List<Long> idList = new ArrayList<Long>();
		idList.add(9l);
		idList.add(10l);
		idList.add(11l);

		if (surveyId > 0) {
			return idList;
		}

		return null;
	}

}
