package CSCI5308.GroupFormationTool.PasswordValidation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import CSCI5308.GroupFormationTool.Database.AbstractDbDependencyFactory;
import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;

public class PasswordValidatorDB implements IPasswordValidatorPersistence
{
	private final Logger logger = LoggerFactory.getLogger(PasswordValidatorDB.class);


	private final IGlobalFactoryProvider globalFactoryProvider;
	private final AbstractDbDependencyFactory dbDependencyFactory;

	public PasswordValidatorDB(IGlobalFactoryProvider globalFactoryProvider) {
		this.globalFactoryProvider = globalFactoryProvider;
		this.dbDependencyFactory = this.globalFactoryProvider.getDbDependencyFactory();
	}

	@Override
	public HashMap<Long,String> loadActivePasswordValidators()
	{
		HashMap<Long,String> activeValidators = new HashMap<Long,String>();
		CallStoredProcedure proc = null;
		try
		{
			proc = new CallStoredProcedure("spLoadActivePasswordValidators()", dbDependencyFactory.getConnectionManager());
			ResultSet results = proc.executeWithResults();
			logger.info(String.valueOf(results));

			if (null != results)
			{
				while (results.next())
				{
					long id = results.getLong(1);
					String name = results.getString(2);
					activeValidators.put(id,name);
				}
			}
		}
		catch (SQLException e)
		{
			logger.error("Unable to load Active Password Validators. Error" + e.getMessage().toString());

		}
		finally
		{
			if (null != proc)
			{
				proc.cleanup();
			}
		}
		return activeValidators;
	}

	@Override
	public String loadConstraintByValidatorId(long id)
	{
		CallStoredProcedure proc = null;
		String constraint=null;
		try
		{
			proc = new CallStoredProcedure("spLoadConstraintByValidator(?)", dbDependencyFactory.getConnectionManager());
			proc.setParameter(1, id);
			ResultSet results = proc.executeWithResults();
			if (null != results)
			{
				while (results.next())
				{
					constraint = results.getString(1);
				}
			}
		}
		catch (SQLException e)
		{
			logger.error("Unable to load Constraint by validator id. Error" + e.getMessage().toString());
		}
		finally
		{
			if (null != proc)
			{
				proc.cleanup();
			}
		}
		return constraint;
	}

	@Override
	public List<String> fetchPreviousPasswordsByBannerID(String bannerID, int constraint)
	{
		CallStoredProcedure proc = null;
		List<String> passwordList = new ArrayList<String>();
		try
		{
			proc = new CallStoredProcedure("spFetchPreviousPasswords(?,?)", dbDependencyFactory.getConnectionManager());
			proc.setParameter(1, bannerID);
			proc.setParameter(2, constraint);
			ResultSet results = proc.executeWithResults();
			if (null != results)
			{
				while (results.next())
				{
					passwordList.add(results.getString(1));
				}
			}
		}
		catch (SQLException e)
		{
			logger.error("Unable to fetch Previous Passwords. Error" + e.getMessage().toString());
		}
		finally
		{
			if (null != proc)
			{
				proc.cleanup();
			}
		}
		return passwordList;
	}

}
