package CSCI5308.GroupFormationTool.SecurityTest;

import CSCI5308.GroupFormationTool.Security.AbstractSecurityFactory;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;

public class SecurityFactoryMock extends AbstractSecurityFactory
{
    @Override
    public IPasswordEncryption getPasswordEncryption()
    {
        return new PasswordEncryptionMock();
    }
}
