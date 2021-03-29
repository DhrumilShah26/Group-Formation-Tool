package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;

public class CourseDependencyFactory extends AbstractCourseDependencyFactory
{
    private ICoursePersistence coursePersistence;
    private ICourseUserRelationshipPersistence courseUserRelationshipPersistence;
    private IStudentCSVParser studentCSVParser;
    private ICourseUserRelationship courseUserRelationship;
    private IStudentCsvImport studentCsvImporter;
    final private IGlobalFactoryProvider globalFactoryProvider;

    public CourseDependencyFactory(IGlobalFactoryProvider globalFactoryProvider) {
        this.globalFactoryProvider = globalFactoryProvider;
    }

    public ICoursePersistence getCoursePersistence()
    {
        if(null == coursePersistence)
        {
            coursePersistence = new CourseDB(globalFactoryProvider);
        }
        return coursePersistence;
    }

    public ICourseUserRelationshipPersistence getCourseUserRelationshipPersistence()
    {
        if(null == courseUserRelationshipPersistence)
        {
            courseUserRelationshipPersistence = new CourseUserRelationshipDB(globalFactoryProvider);
        }
        return courseUserRelationshipPersistence;
    }

    public IStudentCSVParser getStudentCSVParser()
    {
        if(null == studentCSVParser)
        {
            studentCSVParser = new StudentCSVParser();
        }
        return studentCSVParser;
    }

    public ICourseUserRelationship getCourseUserRelationship()
    {
        if(null == courseUserRelationship)
        {
            courseUserRelationship = new CourseUserRelationship(getCourseUserRelationshipPersistence());
        }
        return courseUserRelationship;
    }

    @Override
    public IStudentCsvImport getStudentCsvImport()
    {
        if( null == studentCsvImporter )
        {
            studentCsvImporter = new StudentCSVImport(globalFactoryProvider);
        }
        return studentCsvImporter;
    }
}
