package CSCI5308.GroupFormationTool.QuestionManagerTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import CSCI5308.GroupFormationTool.GlobalDependencyFactory;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.QuestionManager.AbstractQuestionDependencyFactory;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionDependencyFactory;

public class QuestionDependencyFactoryTest {

	private final AbstractQuestionDependencyFactory factory;

	public QuestionDependencyFactoryTest() {
		IGlobalFactoryProvider provider = new GlobalDependencyFactory();
		factory = new QuestionDependencyFactory(provider);
	}

	@Test
	public void getQuestionPersistence() {
		assertNotNull(factory.getQuestionPersistence());
	}

}
