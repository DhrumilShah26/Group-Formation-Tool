package CSCI5308.GroupFormationTool.Security;

import java.util.ArrayList;
import java.util.List;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import CSCI5308.GroupFormationTool.AccessControl.*;

public class CustomAuthenticationManager implements AuthenticationManager
{

	private final
	String DATABASE_CONNECTION_ERROR_MSG = "Error connecting Database please try after some time.",
	GENERIC_ERROR = "Something went wrong. Please try after some time.",
	USER_NOT_FOUND_ERROR = "User not found",
	WRONG_PASSWORD = "Wrong Password";

	private final AbstractAclDependencyFactory aclDependencyFactory;
	private final IPasswordEncryption passwordEncryption;
	private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationManager.class);

	public CustomAuthenticationManager(IGlobalFactoryProvider provider) {
		this.aclDependencyFactory = provider.getAclDependencyFactory();
		this.passwordEncryption = provider.getSecurityFactory().getPasswordEncryption();
	}

	private static final String ADMIN_BANNER_ID = "B-000000";
	
	private Authentication checkAdmin(String password, User u, Authentication authentication) throws AuthenticationException
	{
		logger.trace("Enter checkAdmin Method");
		// The admin password is not encrypted because it is hardcoded in the DB.
		if (password.equals(u.getPassword()))
		{
			logger.debug("User is admin.");
			// Grant ADMIN rights system-wide, this is used to protect controller mappings.
			List<GrantedAuthority> rights = new ArrayList<GrantedAuthority>();
			rights.add(new SimpleGrantedAuthority("ADMIN"));
			// Return valid authentication token.
			UsernamePasswordAuthenticationToken token;
			token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
																			authentication.getCredentials(),
																			rights);
			logger.debug("Exit checkAdmin Method");
			return token;
		}
		else
		{
			logger.info("Entered Admin Password did not match");
			throw new BadCredentialsException(WRONG_PASSWORD);
		}
	}
	
	private Authentication checkNormal(String password, User u, Authentication authentication) throws AuthenticationException
	{
		logger.debug("Attempting to validate password for normal user");
		if (passwordEncryption.matches(password, u.getPassword()))
		{
			// Grant USER rights system-wide, this is used to protect controller mappings.
			List<GrantedAuthority> rights = new ArrayList<GrantedAuthority>();
			rights.add(new SimpleGrantedAuthority("USER"));
			// Return valid authentication token.
			UsernamePasswordAuthenticationToken token;
			token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
																			authentication.getCredentials(),
																			rights);
			return token;
		}
		else
		{
			logger.info("Failed to authenticate Normal user");
			throw new BadCredentialsException(WRONG_PASSWORD);
		}
	}
	
	// Authenticate against our database using the input banner ID and password.
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{

		String bannerID = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		User u = new User();
		try
		{
			logger.debug("Attempting to Authenticate with bannerId="+bannerID);
			aclDependencyFactory.getUserPersistence().loadUserByBannerID(bannerID, u);
		}
		catch (RuntimeSQLException e)
		{
			throw new AuthenticationServiceException(DATABASE_CONNECTION_ERROR_MSG);
		}
		catch (Exception e)
		{
			logger.error("Something went wrong: "+ e.getMessage());
			throw new AuthenticationServiceException(GENERIC_ERROR);
		}
		if (u.isValidUser())
		{
			if (bannerID.toUpperCase().equals(ADMIN_BANNER_ID))
			{
				logger.debug("Attempting to Authenticate as Admin");
				return checkAdmin(password, u, authentication);
			}
			else
			{
				logger.debug("Attempting to Authenticate as Normal User");
				return checkNormal(password, u, authentication);
			}
		}
		else
		{
			logger.error("No user with this banner id found.");
			throw new BadCredentialsException(USER_NOT_FOUND_ERROR);
		}			
	}
}
