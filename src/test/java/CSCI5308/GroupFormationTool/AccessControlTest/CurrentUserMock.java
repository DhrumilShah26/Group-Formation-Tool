package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;
import CSCI5308.GroupFormationTool.AccessControl.ICurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;

public class CurrentUserMock implements ICurrentUser<User>
{
	AbstractAclDependencyFactory factory;
	IUserPersistence userPersistence;

	public CurrentUserMock()
	{
		IGlobalFactoryProvider provider = new GlobalDependencyMock();
		factory = provider.getAclDependencyFactory();
		userPersistence = factory.getUserPersistence();
	}

	public User getCurrentAuthenticatedUser()
	{
		String bannerID = "B00000000";
		User u = factory.makeUserBuilder().build();
		userPersistence.loadUserByBannerID(bannerID, u);
		return u;
	}

}
