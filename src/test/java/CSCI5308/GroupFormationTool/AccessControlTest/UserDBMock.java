package CSCI5308.GroupFormationTool.AccessControlTest;

import java.sql.SQLException;

import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.AccessControl.IUserPersistence;
import CSCI5308.GroupFormationTool.AccessControl.User;

public class UserDBMock implements IUserPersistence {
	public void loadUserByID(long id, User user) {
		user.setID(id);
		user.setBannerID("B00000000");
		user.setPassword("Pass@123");
		user.setFirstName("Rob");
		user.setLastName("Hawkey");
		user.setEmail("group11asdc@gmail.com");
	}

	public void loadUserByBannerID(String bannerID, User user) {
		user.setBannerID(bannerID);
		user.setPassword("Pass@123");
		user.setFirstName("Rob");
		user.setLastName("Hawkey");
		user.setEmail("rhawkey@dal.ca");
		if (bannerID.equalsIgnoreCase("B-DB_CONNECTION_EX")) {
			throw new RuntimeSQLException(new SQLException());
		} else if (bannerID.equalsIgnoreCase("B-NULL_PTR_EX")) {
			throw new NullPointerException();
		} else if (bannerID.equalsIgnoreCase("B-12345")) {
			user.setPassword("encrypted");
			user.setID(1);
		} else if (bannerID.equalsIgnoreCase("B-INVALID")) {
			user.setID(-1);
		} else if (bannerID.equalsIgnoreCase("B-8796")) {
			user.setID(-1);
		} else {
			user.setID(1);
		}
	}

	public boolean createUser(User user) {
		user.setID(0);
		user.setBannerID("B00000000");
		user.setPassword("Pass@123");
		user.setFirstName("Rob");
		user.setLastName("Hawkey");
		user.setEmail("rhawkey@dal.ca");
		return true;
	}

	public boolean updateUser(User user) {
		user.setID(0);
		user.setBannerID("B00000000");
		user.setPassword("Pass@123");
		user.setFirstName("Rob");
		user.setLastName("Hawkey");
		user.setEmail("rhawkey@dal.ca");
		return true;
	}
}
