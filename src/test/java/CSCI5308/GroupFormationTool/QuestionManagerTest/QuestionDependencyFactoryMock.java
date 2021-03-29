package CSCI5308.GroupFormationTool.QuestionManagerTest;

import CSCI5308.GroupFormationTool.QuestionManager.AbstractQuestionDependencyFactory;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionPersistence;

public class QuestionDependencyFactoryMock extends AbstractQuestionDependencyFactory {

	@Override
	public IQuestionPersistence getQuestionPersistence() {
		return new QuestionDBMock();
	}

}
