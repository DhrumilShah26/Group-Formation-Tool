package CSCI5308.GroupFormationTool.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton for retrieving connections.
public class ConnectionManager implements IConnectionManager
{
	
	private final String dbURL;
	private final String dbUserName;
	private final String dbPassword;

	public ConnectionManager(IDatabaseConfiguration config)
	{
		dbURL = config.getDatabaseURL();
		dbUserName = config.getDatabaseUserName();
		dbPassword = config.getDatabasePassword();
	}
	
	public Connection getDBConnection() throws SQLException
	{
		return DriverManager.getConnection(dbURL, dbUserName, dbPassword);
	}
}
