package CSCI5308.GroupFormationTool.Courses;

public abstract class AbstractCourseDependencyFactory {
    abstract public ICoursePersistence getCoursePersistence();
    abstract public ICourseUserRelationshipPersistence getCourseUserRelationshipPersistence();
    abstract public IStudentCSVParser getStudentCSVParser();
    abstract public ICourseUserRelationship getCourseUserRelationship();
    abstract public IStudentCsvImport getStudentCsvImport();
}
