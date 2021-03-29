package CSCI5308.GroupFormationTool.Survey;

import java.util.List;

import CSCI5308.GroupFormationTool.QuestionManager.Question;

public interface ISurveyQuestionPersistence {
	public List<Question> getQuestionBank(String bannerID);

	public boolean addQuestionsToSurvey(List<Long> questionIdList, Long surveyId);

	public List<Long> fetchQuestionBySurvey(Long surveyId);
}
