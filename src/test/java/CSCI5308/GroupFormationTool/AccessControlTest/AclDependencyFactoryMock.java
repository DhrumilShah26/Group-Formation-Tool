package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;
import CSCI5308.GroupFormationTool.AccessControl.ICurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.AccessControl.User;

public class AclDependencyFactoryMock extends AbstractAclDependencyFactory {
    @Override
    public IUserPersistence getUserPersistence() {
        return new UserDBMock();
    }

    @Override
    public ICurrentUser<User> getCurrentUser() {
        return new CurrentUserMock();
    }
}
