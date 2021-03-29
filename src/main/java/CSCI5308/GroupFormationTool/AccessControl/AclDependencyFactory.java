package CSCI5308.GroupFormationTool.AccessControl;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;

public class AclDependencyFactory extends AbstractAclDependencyFactory {

    private IUserPersistence userPersistence;
    private ICurrentUser<User> currentUser;
    private final IGlobalFactoryProvider globalFactoryProvider;

    public AclDependencyFactory(IGlobalFactoryProvider globalFactoryProvider) {
        this.globalFactoryProvider = globalFactoryProvider;
    }

    @Override
    public IUserPersistence getUserPersistence()
    {
        if(null == userPersistence)
        {
            userPersistence = new UserDB(globalFactoryProvider.getDbDependencyFactory().getConnectionManager());
        }
        return userPersistence;
    }

    @Override
    public ICurrentUser<User> getCurrentUser() {
        if(null == currentUser)
        {
            currentUser = new CurrentUser(this);
        }
        return currentUser;
    }
}
