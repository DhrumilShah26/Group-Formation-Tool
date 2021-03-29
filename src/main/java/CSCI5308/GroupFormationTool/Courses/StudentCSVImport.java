package CSCI5308.GroupFormationTool.Courses;

import java.util.ArrayList;
import java.util.List;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.AccessControl.*;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import org.springframework.web.multipart.MultipartFile;

public class StudentCSVImport implements IStudentCsvImport
{
	private final List<String> successResults;
	private final List<String> failureResults;
	private final IPasswordEncryption passwordEncryption;

	public StudentCSVImport(IGlobalFactoryProvider provider)
	{
		successResults = new ArrayList<>();
		failureResults = new ArrayList<>();
		passwordEncryption = provider.getSecurityFactory().getPasswordEncryption();
	}

	public void enrollStudentFromRecord(Course course, MultipartFile file, AbstractCourseDependencyFactory factory,
										AbstractAclDependencyFactory aclDependencyFactory)
	{
		List<User> studentList = factory.getStudentCSVParser().parseCSVFile(file, failureResults);
		for(User u : studentList)
		{
			String bannerID = u.getBanner();
			String firstName = u.getFirstName();
			String lastName = u.getLastName();
			String email = u.getEmail();
			String userDetails = bannerID + " " + firstName + " " + lastName +" " + email;

			User user = new User();
			aclDependencyFactory.getUserPersistence().loadUserByBannerID(bannerID, user);

			if (user.isValidUser() == false)
			{
				user.setBannerID(bannerID);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				if (user.createUser(aclDependencyFactory.getUserPersistence(), passwordEncryption, null))
				{
					successResults.add("Created: " + userDetails);
					aclDependencyFactory.getUserPersistence().loadUserByBannerID(bannerID, user);
				}
				else
				{
					failureResults.add("Unable to save this user to DB: " + userDetails);
					return;
				}
			}
			if (course.enrollUserInCourse(Role.STUDENT, user, factory.getCourseUserRelationship()))
			{
				successResults.add("User enrolled in course: " + userDetails);
			}else
			{
				failureResults.add("Unable to enroll user in course: " + userDetails);
			}
		}
	}

	public List<String> getSuccessResults()
	{
		return successResults;
	}

	public List<String> getFailureResults()
	{
		return failureResults;
	}

}
