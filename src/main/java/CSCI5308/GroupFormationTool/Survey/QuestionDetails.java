package CSCI5308.GroupFormationTool.Survey;

public class QuestionDetails {
	
	Integer surveyID;
	Integer questionID;
	String questionText;
	String questionType;
	
	
	
	public Integer getSurveyID() {
		return surveyID;
	}
	public void setSurveyID(Integer surveyID) {
		this.surveyID = surveyID;
	}
	public Integer getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Integer questionID) {
		this.questionID = questionID;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	@Override
	public String toString() {
		return "QuestionDetails [surveyID=" + surveyID + ", questionID=" + questionID + ", questionText=" + questionText
				+ ", questionType=" + questionType + "]";
	}
	

}
