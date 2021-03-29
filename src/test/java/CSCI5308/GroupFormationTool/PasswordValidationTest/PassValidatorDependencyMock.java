package CSCI5308.GroupFormationTool.PasswordValidationTest;

import CSCI5308.GroupFormationTool.PasswordValidation.AbstractPasswordValidatorDependencyFactory;
import CSCI5308.GroupFormationTool.PasswordValidation.IPasswordValidatorEnumerator;
import CSCI5308.GroupFormationTool.PasswordValidation.IPasswordValidatorPersistence;

public class PassValidatorDependencyMock extends AbstractPasswordValidatorDependencyFactory
{
    IPasswordValidatorEnumerator pve;

    @Override
    public IPasswordValidatorPersistence getPasswordValidatorPersistence()
    {
        return new PasswordValidatorDBMock();
    }


    @Override
    public IPasswordValidatorEnumerator getPasswordValidatorEnumerator()
    {
        if (null == pve)
        {
            pve = new PasswordValidatorEnumeratorMock();
        }
        return pve;
    }
}
