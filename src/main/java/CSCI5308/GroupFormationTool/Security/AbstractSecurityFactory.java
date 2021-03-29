package CSCI5308.GroupFormationTool.Security;

public abstract class AbstractSecurityFactory {
    abstract public IPasswordEncryption getPasswordEncryption();
}
