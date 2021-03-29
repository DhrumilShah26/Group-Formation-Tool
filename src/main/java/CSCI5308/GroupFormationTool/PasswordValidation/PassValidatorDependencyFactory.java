package CSCI5308.GroupFormationTool.PasswordValidation;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;

public class PassValidatorDependencyFactory extends AbstractPasswordValidatorDependencyFactory
{

    final IGlobalFactoryProvider globalFactoryProvider;
    private IPasswordValidatorPersistence persistence;
    private IPasswordValidatorEnumerator passwordValidatorEnumerator;

    public PassValidatorDependencyFactory(IGlobalFactoryProvider globalFactoryProvider)
    {
        this.globalFactoryProvider = globalFactoryProvider;
    }

    @Override
    public IPasswordValidatorPersistence getPasswordValidatorPersistence()
    {
        if( null == persistence)
        {
            persistence = new PasswordValidatorDB(globalFactoryProvider);
        }
        return persistence;
    }

    @Override
    public IPasswordValidatorEnumerator getPasswordValidatorEnumerator()
    {
        if( null == passwordValidatorEnumerator ) {
            passwordValidatorEnumerator = new PasswordValidatorEnumerator(globalFactoryProvider);
        }
        return passwordValidatorEnumerator;
    }
}
