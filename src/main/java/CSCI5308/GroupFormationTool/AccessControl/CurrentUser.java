package CSCI5308.GroupFormationTool.AccessControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class CurrentUser implements ICurrentUser<User>
{
	AbstractAclDependencyFactory factory;
	IUserPersistence userPersistence;

	Logger log = LoggerFactory.getLogger(CurrentUser.class);
	public CurrentUser(AbstractAclDependencyFactory factory)
	{
		this.factory = factory;
		userPersistence = this.factory.getUserPersistence();
	}

	@Override
	public User getCurrentAuthenticatedUser()
	{
		log.trace("Entering getCurrentAuthenticatedUser method");
		try {
			log.debug("Attempting to get LoggedIn current User");
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication.isAuthenticated())
			{
				String bannerID = authentication.getPrincipal().toString();
				log.debug("Current user BannerID="+bannerID);
				User u = factory.makeUserBuilder().build();
				log.trace("Loading Current User BannerID="+bannerID);
				userPersistence.loadUserByBannerID(bannerID, u);
				log.debug("User Loaded from DB="+bannerID);
				if (u.isValidUser())
				{
					log.debug("User is Valid BannerId = "+ bannerID);
					return u;
				}
			}
		} catch (Exception e) {
			log.error("Error getting current User "+e.getMessage());
		} finally {
			log.trace("Exiting getCurrentAuthenticatedUser method");
		}
		return null;
	}
	
}
