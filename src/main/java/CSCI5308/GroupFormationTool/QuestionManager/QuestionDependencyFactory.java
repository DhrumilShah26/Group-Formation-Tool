package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;

public class QuestionDependencyFactory extends AbstractQuestionDependencyFactory {

    final private IGlobalFactoryProvider provider;
    private QuestionDB questionDB;

    public QuestionDependencyFactory(IGlobalFactoryProvider provider) {
        this.provider = provider;
    }

    @Override
    public IQuestionPersistence getQuestionPersistence() {
        if(null == questionDB)
        {
            questionDB = new QuestionDB(provider.getDbDependencyFactory().getConnectionManager());
        }
        return questionDB;
    }
}
