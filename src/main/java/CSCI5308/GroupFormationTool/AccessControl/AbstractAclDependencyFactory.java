package CSCI5308.GroupFormationTool.AccessControl;

public abstract class AbstractAclDependencyFactory {
    public abstract IUserPersistence getUserPersistence();
    public abstract ICurrentUser<User> getCurrentUser();
    public IUserBuilder makeUserBuilder() {
        return IUserBuilder.getDefaultBuilder();
    }
}
