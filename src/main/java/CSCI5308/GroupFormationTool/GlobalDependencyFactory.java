package CSCI5308.GroupFormationTool;

import org.springframework.stereotype.Service;

import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;
import CSCI5308.GroupFormationTool.AccessControl.AclDependencyFactory;
import CSCI5308.GroupFormationTool.Courses.AbstractCourseDependencyFactory;
import CSCI5308.GroupFormationTool.Courses.CourseDependencyFactory;
import CSCI5308.GroupFormationTool.Database.AbstractDbDependencyFactory;
import CSCI5308.GroupFormationTool.Database.DbDependencyFactory;
import CSCI5308.GroupFormationTool.PasswordValidation.AbstractPasswordValidatorDependencyFactory;
import CSCI5308.GroupFormationTool.PasswordValidation.PassValidatorDependencyFactory;
import CSCI5308.GroupFormationTool.QuestionManager.AbstractQuestionDependencyFactory;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionDependencyFactory;
import CSCI5308.GroupFormationTool.Security.AbstractSecurityFactory;
import CSCI5308.GroupFormationTool.Security.SecurityFactory;
import CSCI5308.GroupFormationTool.Survey.AbstractSurveyDependencyFactory;
import CSCI5308.GroupFormationTool.Survey.SurveyDependencyFactory;

@Service
public class GlobalDependencyFactory implements IGlobalFactoryProvider {

	private AbstractAclDependencyFactory aclDependencyFactory;
	private AbstractDbDependencyFactory dbDependencyFactory;
	private AbstractCourseDependencyFactory courseDependencyFactory;
	private AbstractPasswordValidatorDependencyFactory passwordValidatorDependencyFactory;
	private AbstractQuestionDependencyFactory questionDependencyFactory;
	private AbstractSurveyDependencyFactory surveyDependencyFactory;
	private AbstractSecurityFactory securityFactory;

	@Override
	public AbstractAclDependencyFactory getAclDependencyFactory() {
		if (null == aclDependencyFactory) {
			aclDependencyFactory = new AclDependencyFactory(this);
		}
		return aclDependencyFactory;
	}

	@Override
	public AbstractDbDependencyFactory getDbDependencyFactory() {
		if (null == dbDependencyFactory) {
			dbDependencyFactory = new DbDependencyFactory(this);
		}
		return dbDependencyFactory;
	}

	@Override
	public AbstractCourseDependencyFactory getCourseDependencyFactory() {
		if (null == courseDependencyFactory) {
			courseDependencyFactory = new CourseDependencyFactory(this);
		}
		return courseDependencyFactory;
	}

	@Override
	public AbstractPasswordValidatorDependencyFactory getPasswordValidatorDependencyFactory() {
		if (null == passwordValidatorDependencyFactory) {
			passwordValidatorDependencyFactory = new PassValidatorDependencyFactory(this);
		}
		return passwordValidatorDependencyFactory;
	}

	@Override
	public AbstractQuestionDependencyFactory getQuestionDependencyFactory() {
		if (null == questionDependencyFactory) {
			questionDependencyFactory = new QuestionDependencyFactory(this);
		}
		return questionDependencyFactory;
	}

	@Override
	public AbstractSecurityFactory getSecurityFactory() {
		if (null == securityFactory) {
			securityFactory = new SecurityFactory();
		}
		return securityFactory;
	}

	@Override
	public AbstractSurveyDependencyFactory getSurveyDependencyFactory() {
		if (null == surveyDependencyFactory) {
			surveyDependencyFactory = new SurveyDependencyFactory(this);
		}
		return surveyDependencyFactory;
	}
}
