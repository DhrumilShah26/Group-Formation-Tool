package CSCI5308.GroupFormationTool.Survey;

import java.util.List;

public interface ISurveyPersistence {

	public long createSurvey(Survey survey, long courseID);

	public List<Long> getStudentByCourse(long courseID);

	public Survey getSurveyByCourse(long courseID);

	public boolean updateSurveyStatus(Long surveyID, String status);

	public List<QuestionDetails> getSurveyforStudent(long courseID);

	List<QuestionOption> getQuestionOptionforStudent(long questionID);
}
