package CSCI5308.GroupFormationTool.Security;

public class SecurityFactory extends AbstractSecurityFactory {

    private IPasswordEncryption passwordEncryption;

    @Override
    public IPasswordEncryption getPasswordEncryption() {
        if(null == passwordEncryption) {
            passwordEncryption = new BCryptPasswordEncryption();
        }
        return passwordEncryption;
    }
}
