package CSCI5308.GroupFormationTool.Courses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.Database.AbstractDbDependencyFactory;
import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;

public class CourseDB implements ICoursePersistence {
	final IGlobalFactoryProvider globalFactoryProvider;
	final AbstractDbDependencyFactory dbDependency;

	public CourseDB(IGlobalFactoryProvider globalFactoryProvider) {
		this.globalFactoryProvider = globalFactoryProvider;
		this.dbDependency = globalFactoryProvider.getDbDependencyFactory();
	}

	private final Logger logger = LoggerFactory.getLogger(CourseDB.class);

	public List<Course> loadAllCourses() {
		List<Course> courses = new ArrayList<Course>();
		CallStoredProcedure proc = null;
		try {
			logger.info("Fetching all the courses");
			proc = new CallStoredProcedure("spLoadAllCourses()", dbDependency.getConnectionManager());
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					long id = results.getLong(1);
					String title = results.getString(2);
					Course c = new Course();
					c.setId(id);
					c.setTitle(title);
					courses.add(c);
				}
			}
		}
		catch (SQLException e)
		{
			logger.error("Courses are not found! Error: "+e.getMessage().toString());
		}
		finally
		{
			if (null != proc)
			{
				proc.cleanup();
			}
		}
		return courses;
	}

	public void loadCourseByID(long id, Course course) {
		CallStoredProcedure proc = null;
		try {
			logger.info("Fetching course by ID");
			proc = new CallStoredProcedure("spFindCourseByID(?)", dbDependency.getConnectionManager());
			proc.setParameter(1, id);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					String title = results.getString(2);
					course.setId(id);
					course.setTitle(title);
				}
			}
		}
		catch (SQLException e)
		{
			logger.error("The course was not found by given ID! Error: "+e.getMessage().toString());
		}
		finally
		{
			if (null != proc)
			{
				proc.cleanup();
			}
		}
	}

	public boolean createCourse(Course course)
	{
		CallStoredProcedure proc = null;
		try {
			logger.info("Creating a course");
			proc = new CallStoredProcedure("spCreateCourse(?, ?)", dbDependency.getConnectionManager());
			proc.setParameter(1, course.getTitle());
			proc.registerOutputParameterLong(2);
			proc.execute();
		} catch (SQLException e) {
			logger.error("Could not create a course! Error : " + e.getMessage().toString());
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

	public boolean deleteCourse(long id)
	{
		CallStoredProcedure proc = null;
		try {
			logger.info("Deleting the course based on given ID");
			proc = new CallStoredProcedure("spDeleteCourse(?)", dbDependency.getConnectionManager());
			proc.setParameter(1, id);
			proc.execute();
		} catch (SQLException e) {
			logger.error("Could not delete the course! Error: " + e.getMessage().toString());
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
}
