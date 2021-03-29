package CSCI5308.GroupFormationTool.PasswordValidation;

public abstract class AbstractPasswordValidatorDependencyFactory {
    abstract public IPasswordValidatorPersistence getPasswordValidatorPersistence();
    abstract public IPasswordValidatorEnumerator getPasswordValidatorEnumerator();
}
