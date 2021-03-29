package CSCI5308.GroupFormationTool.SurveyTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import CSCI5308.GroupFormationTool.GlobalDependencyFactory;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.Survey.AbstractSurveyDependencyFactory;
import CSCI5308.GroupFormationTool.Survey.SurveyDependencyFactory;

public class SurveyDependencyFactoryTest {
	private AbstractSurveyDependencyFactory factory;

	public SurveyDependencyFactoryTest() {
		IGlobalFactoryProvider provider = new GlobalDependencyFactory();
		factory = new SurveyDependencyFactory(provider);
	}

	@Test
	public void getSurveyPersistence() {
		assertNotNull(factory.getSurveyPersistence());
	}

	@Test
	public void getSurveyQuestionPersistence() {
		assertNotNull(factory.getSurveyQuestionPersistence());
	}
}
