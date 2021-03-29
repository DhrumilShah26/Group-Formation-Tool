package CSCI5308.GroupFormationTool.Survey;

public abstract class AbstractSurveyDependencyFactory {
	abstract public ISurveyPersistence getSurveyPersistence();

	abstract public ISurveyQuestionPersistence getSurveyQuestionPersistence();

	public AbstractGroupingStrategyFactory getGroupingStrategyFactory() {
		return new GroupingStrategyFactory();
	}
}
