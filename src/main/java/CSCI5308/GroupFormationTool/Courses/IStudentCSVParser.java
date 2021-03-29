package CSCI5308.GroupFormationTool.Courses;

import java.util.List;

import CSCI5308.GroupFormationTool.AccessControl.User;
import org.springframework.web.multipart.MultipartFile;

public interface IStudentCSVParser 
{
	List<User> parseCSVFile(MultipartFile file, List<String> failureResults);
}
