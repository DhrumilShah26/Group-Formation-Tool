package CSCI5308.GroupFormationTool.SurveyTest;

import java.util.ArrayList;
import java.util.List;

import CSCI5308.GroupFormationTool.Survey.ISurveyPersistence;
import CSCI5308.GroupFormationTool.Survey.QuestionDetails;
import CSCI5308.GroupFormationTool.Survey.QuestionOption;
import CSCI5308.GroupFormationTool.Survey.Survey;
import CSCI5308.GroupFormationTool.Survey.SurveyStatus;

public class SurveyDBMock implements ISurveyPersistence {

	@Override
	public long createSurvey(Survey survey, long courseID) {
		Long surveyID = -1l;
		if (courseID > 0 && survey != null) {
			surveyID = survey.getSurveyId();
		}
		return surveyID;
	}

	@Override
	public Survey getSurveyByCourse(long courseID) {

		if (courseID == 9009) {
			return null;
		}
		if (courseID == 9099) {
			return Survey.getSurveyBuilder().setSurveyID(-1).setSurveyStatus(SurveyStatus.PUBLISHED)
					.setSurveyTitle("mysurvey").setUserBannerID("B-000000").buildSurvey();
		}
		if (courseID == 9900) {
			return Survey.getSurveyBuilder().setSurveyID(9900).setSurveyStatus(SurveyStatus.PUBLISHED)
					.setSurveyTitle("mysurvey").setUserBannerID("B-000000").buildSurvey();
		}
		if (courseID == 9999) {
			return Survey.getSurveyBuilder().setSurveyID(9999).setSurveyStatus(SurveyStatus.DRAFT)
					.setSurveyTitle("mysurvey").setUserBannerID("B-000000").buildSurvey();
		}
		if (courseID > 0) {
			return Survey.getSurveyBuilder().setSurveyID(1).setSurveyStatus(SurveyStatus.DRAFT)
					.setSurveyTitle("mysurvey").setUserBannerID("B-000000").buildSurvey();
		}
		return null;
	}

	@Override
	public boolean updateSurveyStatus(Long surveyID, String status) {
		if (surveyID < 1 || (status != SurveyStatus.DRAFT.toString() && status != SurveyStatus.PUBLISHED.toString())) {
			return false;
		}
		return true;
	}

	@Override
	public List<QuestionDetails> getSurveyforStudent(long courseID) {
		List<QuestionDetails> questionDetailsList = new ArrayList<QuestionDetails>();
		QuestionDetails questionDetails = new QuestionDetails();
		questionDetails.setQuestionID(1);
		questionDetails.setQuestionText("Question 1");
		questionDetailsList.add(questionDetails);
		questionDetails.setQuestionID(2);
		questionDetails.setQuestionText("Question 2");
		questionDetailsList.add(questionDetails);

		if (courseID > 0) {
			return questionDetailsList;
		}

		return null;
	}

	@Override
	public List<QuestionOption> getQuestionOptionforStudent(long questionID) {
		List<QuestionOption> questionOptionList = new ArrayList<QuestionOption>();
		QuestionOption questionOption = new QuestionOption();
		questionOption.setDisplayText("Text 1");
		questionOption.setStoredAs("1");
		questionOptionList.add(questionOption);
		questionOption.setDisplayText("Text 2");
		questionOption.setStoredAs("2");
		questionOptionList.add(questionOption);

		if (questionID > 0) {
			return questionOptionList;
		}

		return null;
	}

	@Override
	public List<Long> getStudentByCourse(long courseID) {
		List<Long> studentIdList = new ArrayList<Long>();
		studentIdList.add(1l);
		studentIdList.add(2l);

		if (courseID < 1) {
			return null;
		}
		return studentIdList;
	}

}
