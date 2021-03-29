package CSCI5308.GroupFormationTool.Survey;

import java.util.List;

public class SurveyResponse {
	
	int userID;
	int surveyID;
	List<QuestionResponse> questionResponse;
	
	
	public List<QuestionResponse> getQuestionResponse() {
		return questionResponse;
	}


	public void setQuestionResponse(List<QuestionResponse> questionResponse) {
		this.questionResponse = questionResponse;
	}


	public int getUserID() {
		return userID;
	}
	
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
	public int getSurveyID() {
		return surveyID;
	}
	
	
	public void setSurveyID(int surveyID) {
		this.surveyID = surveyID;
	}
	
	
}
