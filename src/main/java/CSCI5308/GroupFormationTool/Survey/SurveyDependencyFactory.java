package CSCI5308.GroupFormationTool.Survey;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;

public class SurveyDependencyFactory extends AbstractSurveyDependencyFactory {

	final private IGlobalFactoryProvider provider;
	private SurveyDB surveyDB;
	private SurveyQuestionDB surveyQuestionDB;

	public SurveyDependencyFactory(IGlobalFactoryProvider provider) {
		this.provider = provider;
	}

	@Override
	public ISurveyPersistence getSurveyPersistence() {
		if (null == surveyDB) {
			surveyDB = new SurveyDB(provider.getDbDependencyFactory().getConnectionManager());
		}
		return surveyDB;
	}

	@Override
	public ISurveyQuestionPersistence getSurveyQuestionPersistence() {
		if (null == surveyQuestionDB) {
			surveyQuestionDB = new SurveyQuestionDB(provider.getDbDependencyFactory().getConnectionManager());
		}
		return surveyQuestionDB;
	}

}
