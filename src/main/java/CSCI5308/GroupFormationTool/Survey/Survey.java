package CSCI5308.GroupFormationTool.Survey;

public class Survey {

	private long id;
	private String bannerID;
	private String surveyTitle;
	private SurveyStatus status;

	Survey() {

	}

	public long getSurveyId() {
		return id;
	}

	public String getUserBannerId() {
		return bannerID;
	}

	public String getSurveyTitle() {
		return surveyTitle;
	}

	public SurveyStatus getStatus() {
		return status;
	}

	public long createSurvey(ISurveyPersistence surveyDB, long courseID) {
		return surveyDB.createSurvey(this, courseID);
	}

	public Survey getSurveyByCourse(ISurveyPersistence surveyDB, long courseID) {
		return surveyDB.getSurveyByCourse(courseID);
	}

	public boolean updateSurveyStatus(ISurveyPersistence surveyDB, String status, long surveyID) {
		return surveyDB.updateSurveyStatus(surveyID, status);
	}

	public static ISurveyBuilder getSurveyBuilder() {
		return new SurveyBuilder();
	}

	public static class SurveyBuilder implements ISurveyBuilder {
		private long id;
		private String bannerID;
		private String surveyTitle;
		private SurveyStatus status;

		public SurveyBuilder() {

		}

		@Override
		public SurveyBuilder setSurveyTitle(String surveyTitle) {
			this.surveyTitle = surveyTitle;
			return this;
		}

		@Override
		public SurveyBuilder setUserBannerID(String bannerID) {
			this.bannerID = bannerID;
			return this;
		}

		@Override
		public SurveyBuilder setSurveyStatus(SurveyStatus status) {
			this.status = status;
			return this;
		}

		@Override
		public SurveyBuilder setSurveyID(long id) {
			this.id = id;
			return this;
		}

		public Survey buildSurvey() {
			Survey survey = new Survey();
			survey.bannerID = this.bannerID;
			survey.id = this.id;
			survey.surveyTitle = this.surveyTitle;
			survey.status = this.status;
			return survey;
		}
	}

}
