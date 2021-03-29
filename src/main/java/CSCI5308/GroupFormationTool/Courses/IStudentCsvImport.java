package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IStudentCsvImport {
    void enrollStudentFromRecord(Course course, MultipartFile file, AbstractCourseDependencyFactory factory,
                                 AbstractAclDependencyFactory aclDependencyFactory);
    List<String> getSuccessResults();
    List<String> getFailureResults();
}
