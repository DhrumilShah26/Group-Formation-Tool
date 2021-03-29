package CSCI5308.GroupFormationTool.SurveyTest;

import CSCI5308.GroupFormationTool.Survey.AbstractSurveyDependencyFactory;
import CSCI5308.GroupFormationTool.Survey.ISurveyPersistence;
import CSCI5308.GroupFormationTool.Survey.ISurveyQuestionPersistence;

public class SurveyDependencyFactoryMock extends AbstractSurveyDependencyFactory {

	@Override
	public ISurveyPersistence getSurveyPersistence() {
		return new SurveyDBMock();
	}

	@Override
	public ISurveyQuestionPersistence getSurveyQuestionPersistence() {
		return new SurveyQuestionDBMock();
	}

}
