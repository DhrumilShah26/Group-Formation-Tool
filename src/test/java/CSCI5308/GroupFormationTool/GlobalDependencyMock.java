package CSCI5308.GroupFormationTool;

import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;
import CSCI5308.GroupFormationTool.AccessControlTest.AclDependencyFactoryMock;
import CSCI5308.GroupFormationTool.Courses.AbstractCourseDependencyFactory;
import CSCI5308.GroupFormationTool.CoursesTest.CourseDependencyMock;
import CSCI5308.GroupFormationTool.Database.AbstractDbDependencyFactory;
import CSCI5308.GroupFormationTool.PasswordValidation.AbstractPasswordValidatorDependencyFactory;
import CSCI5308.GroupFormationTool.PasswordValidationTest.PassValidatorDependencyMock;
import CSCI5308.GroupFormationTool.QuestionManager.AbstractQuestionDependencyFactory;
import CSCI5308.GroupFormationTool.QuestionManagerTest.QuestionDependencyFactoryMock;
import CSCI5308.GroupFormationTool.Security.AbstractSecurityFactory;
import CSCI5308.GroupFormationTool.SecurityTest.SecurityFactoryMock;
import CSCI5308.GroupFormationTool.Survey.AbstractSurveyDependencyFactory;
import CSCI5308.GroupFormationTool.SurveyTest.SurveyDependencyFactoryMock;

public class GlobalDependencyMock implements IGlobalFactoryProvider {

	@Override
	public AbstractAclDependencyFactory getAclDependencyFactory() {
		return new AclDependencyFactoryMock();
	}

	@Override
	public AbstractDbDependencyFactory getDbDependencyFactory() {
		return null;
	}

    @Override
    public AbstractCourseDependencyFactory getCourseDependencyFactory() {
        return new CourseDependencyMock(this);
    }

	@Override
	public AbstractPasswordValidatorDependencyFactory getPasswordValidatorDependencyFactory() {
		return new PassValidatorDependencyMock();
	}

	@Override
	public AbstractQuestionDependencyFactory getQuestionDependencyFactory() {
		return new QuestionDependencyFactoryMock();
	}

	@Override
	public AbstractSurveyDependencyFactory getSurveyDependencyFactory() {
		return new SurveyDependencyFactoryMock();
	}

	@Override
	public AbstractSecurityFactory getSecurityFactory() {
		return new SecurityFactoryMock();
	}
}
