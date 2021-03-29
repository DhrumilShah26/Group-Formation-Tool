package CSCI5308.GroupFormationTool.AccessControl;

import java.sql.ResultSet;
import java.sql.SQLException;

import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.Database.ConnectionManager;
import CSCI5308.GroupFormationTool.Database.IConnectionManager;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDB implements IUserPersistence
{
	Logger logger = LoggerFactory.getLogger(UserDB.class);
	private final IConnectionManager connectionManager;

	public UserDB(IConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public void loadUserByID(long id, User user)
	{
		logger.trace("Entering loadUserByID: ID="+id);
		CallStoredProcedure proc = null;
		try
		{
			logger.debug("Attempting to load user from DB: UserID="+id);
			proc = new CallStoredProcedure("spLoadUser(?)", connectionManager);
			proc.setParameter(1, id);
			ResultSet results = proc.executeWithResults();
			if (null != results)
			{
				while (results.next())
				{
					logger.debug("Attempting to load userObject from SQL Result");
					long userID = results.getLong(1);
					String bannerID = results.getString(2);
					String password = results.getString(3);
					String firstName = results.getString(4);
					String lastName = results.getString(5);
					String email = results.getString(6);
					user.setID(userID);
					user.setBannerID(bannerID);
					user.setPassword(password);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setEmail(email);
				}
			}
			else
			{
				logger.debug("User Not found with UserID="+id);
			}
		}
		catch (SQLException e)
		{
			logger.error("Something went wrong loading the user with UserID="+id+" Error="+e.getMessage());
		}
		finally
		{
			logger.trace("Attempting to perform cleanup");
			if (null != proc)
			{
				proc.cleanup();
			}
		}
		logger.trace("Exiting loadUserByID method.");
	}

	public void loadUserByBannerID(String bannerID, User user) {
		logger.trace("Entering loadUserByBannerID: BannerID="+bannerID);
		CallStoredProcedure proc = null;
		long userID = -1;
		try
		{
			logger.debug("Attempting to findUserBy BannerID="+bannerID);
			proc = new CallStoredProcedure("spFindUserByBannerID(?)", connectionManager);
			proc.setParameter(1, bannerID);
			ResultSet results = proc.executeWithResults();
			if (null != results)
			{
				while (results.next())
				{
					userID = results.getLong(1);
				}
			} else {
				logger.debug("User not found for BannerID="+bannerID);
			}
		}
		catch (SQLException e)
		{
			logger.error("Something went wrong loading the user with bannerId="+bannerID);
			logger.error("Error: "+e.getMessage());
			throw new RuntimeSQLException(e);
		}
		finally
		{
			logger.trace("Attempting to perform cleanup");
			if (null != proc)
			{
				proc.cleanup();
			}
		}
		// If we found the ID load the full details.
		if (userID > -1)
		{
			loadUserByID(userID, user);
		}
	}
	
	public boolean createUser(User user) {
		logger.trace("Entering createUser: BannerID="+user.getBannerID());
		CallStoredProcedure proc = null;
		try
		{
			logger.debug("Attempting to Save user to the Database: BannerID="+user.getBannerID());
			proc = new CallStoredProcedure("spCreateUser(?, ?, ?, ?, ?, ?)", connectionManager);
			proc.setParameter(1, user.getBannerID());
			proc.setParameter(2, user.getPassword());
			proc.setParameter(3, user.getFirstName());
			proc.setParameter(4, user.getLastName());
			proc.setParameter(5, user.getEmail());
			proc.registerOutputParameterLong(6);
			proc.execute();
			logger.debug("createUser attempt was Successful: BannerID="+user.getBannerID());
		}
		catch (SQLException e)
		{
			logger.error("Error creating User with BannerID="+user.getBannerID());
			logger.error("Error: " + e.getMessage());
			throw new RuntimeSQLException(e);
		}
		finally
		{
			logger.trace("Attempting to perform cleanup");
			if (null != proc)
			{
				proc.cleanup();
			}
		}
		return true;
	}
	
	public boolean updateUser(User user)
	{
		return false;
	}
}
