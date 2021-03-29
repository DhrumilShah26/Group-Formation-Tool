package CSCI5308.GroupFormationTool.Courses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.Database.AbstractDbDependencyFactory;
import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;

public class CourseUserRelationshipDB implements ICourseUserRelationshipPersistence {
	private final Logger logger = LoggerFactory.getLogger(CourseUserRelationshipDB.class);

	final IGlobalFactoryProvider globalFactoryProvider;
	final AbstractDbDependencyFactory dbDependency;

	public CourseUserRelationshipDB(IGlobalFactoryProvider globalFactoryProvider) {
		this.globalFactoryProvider = globalFactoryProvider;
		dbDependency = globalFactoryProvider.getDbDependencyFactory();
	}

	public List<User> findAllUsersWithoutCourseRole(Role role, long courseID) {

		List<User> users = new ArrayList<User>();
		CallStoredProcedure proc = null;
		try {
			logger.info("Find all user without course role");
			proc = new CallStoredProcedure("spFindUsersWithoutCourseRole(?, ?)", dbDependency.getConnectionManager());
			proc.setParameter(1, role.toString());
			proc.setParameter(2, courseID);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					long userID = results.getLong(1);
					String bannerID = results.getString(2);
					String firstName = results.getString(3);
					String lastName = results.getString(4);
					User u = new User();
					u.setID(userID);
					u.setBannerID(bannerID);
					u.setFirstName(firstName);
					u.setLastName(lastName);
					users.add(u);
				}
			}
		} catch (SQLException e) {
			logger.error("Could not find users! Error: " + e.getMessage().toString());
			throw new RuntimeSQLException(e);
		} catch (Exception exc) {
			System.out.println(exc);
			logger.error("Error Loading Resources: " + exc.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return users;
	}

	public List<User> findAllUsersWithCourseRole(Role role, long courseID) {
		List<User> users = new ArrayList<User>();
		CallStoredProcedure proc = null;
		try {
			proc = new CallStoredProcedure("spFindUsersWithCourseRole(?, ?)", dbDependency.getConnectionManager());
			logger.info("Finding user with given role");
			proc.setParameter(1, role.toString());
			proc.setParameter(2, courseID);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					long userID = results.getLong(1);
					User u = new User();
					u.setID(userID);
					users.add(u);
				}
			}
		} catch (SQLException e) {
			logger.error("Could not find user by given role! Error: " + e.getMessage().toString());
			throw new RuntimeSQLException(e);
		} catch (Exception exc) {
			System.out.println(exc);
			logger.error("Error Loading Resources: " + exc.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return users;
	}

	public boolean enrollUser(Course course, User user, Role role) {
		CallStoredProcedure proc = null;
		try {
			logger.info("Enrolling a user in course with the specific role");
			proc = new CallStoredProcedure("spEnrollUser(?, ?, ?)", dbDependency.getConnectionManager());
			proc.setParameter(1, course.getId());
			proc.setParameter(2, user.getID());
			proc.setParameter(3, role.toString());
			proc.execute();
		} catch (SQLException e) {
			logger.error("Could not enrolled a user! Error: " + e.getMessage().toString());
			return false;
		} catch (Exception exc) {
			System.out.println(exc);
			logger.error("Error Loading Resources: " + exc.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return true;
	}

	public List<Role> loadUserRolesForCourse(Course course, User user) {
		List<Role> roles = new ArrayList<Role>();
		CallStoredProcedure proc = null;
		try {
			logger.info("Loading roles for user of a course");
			proc = new CallStoredProcedure("spLoadUserRolesForCourse(?, ?)", dbDependency.getConnectionManager());
			proc.setParameter(1, course.getId());
			proc.setParameter(2, user.getID());
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					Role role = Role.valueOf(results.getString(1).toUpperCase());
					roles.add(role);
				}
			}
		} catch (SQLException e) {
			logger.error("Could not load the roles! Error: " + e.getMessage().toString());
			throw new RuntimeSQLException(e);
		} catch (Exception exc) {
			System.out.println(exc);
			logger.error("Error Loading Resources: " + exc.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return roles;
	}
}
