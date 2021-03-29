package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.Survey.Survey.SurveyBuilder;

public interface ISurveyBuilder {
	public SurveyBuilder setSurveyTitle(String surveyTitle);

	public SurveyBuilder setUserBannerID(String bannerID);

	public SurveyBuilder setSurveyStatus(SurveyStatus status);

	public SurveyBuilder setSurveyID(long id);
}
