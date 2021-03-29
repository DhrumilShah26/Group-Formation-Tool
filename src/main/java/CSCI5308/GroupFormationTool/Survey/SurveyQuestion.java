package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;
import java.util.List;

import CSCI5308.GroupFormationTool.QuestionManager.Question;

public class SurveyQuestion {
	public List<Long> questionIdList;

	public SurveyQuestion() {
		setDefault();
	}

	public void setDefault() {
		questionIdList = new ArrayList<Long>();
	}

	public List<Question> getQuestionBank(ISurveyQuestionPersistence surveyQuestionDB, String bannerID) {
		return surveyQuestionDB.getQuestionBank(bannerID);
	}

	public List<Long> getQuestionList() {
		return questionIdList;
	}

	public void setQuestionList(List<Long> questionIdList) {
		this.questionIdList = questionIdList;
	}

	public boolean saveQuestionsDraft(ISurveyQuestionPersistence surveyQuestionDB, List<Long> questionIdList,
			Long surveyID) {
		return surveyQuestionDB.addQuestionsToSurvey(questionIdList, surveyID);
	}

	public List<Long> fetchSurveyQuestions(ISurveyQuestionPersistence surveyQuestionDB, Long surveyID) {
		return surveyQuestionDB.fetchQuestionBySurvey(surveyID);
	}
}
