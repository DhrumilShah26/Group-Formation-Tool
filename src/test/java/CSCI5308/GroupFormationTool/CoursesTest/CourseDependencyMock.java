package CSCI5308.GroupFormationTool.CoursesTest;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.Courses.AbstractCourseDependencyFactory;
import CSCI5308.GroupFormationTool.Courses.ICoursePersistence;
import CSCI5308.GroupFormationTool.Courses.ICourseUserRelationship;
import CSCI5308.GroupFormationTool.Courses.ICourseUserRelationshipPersistence;
import CSCI5308.GroupFormationTool.Courses.IStudentCSVParser;
import CSCI5308.GroupFormationTool.Courses.IStudentCsvImport;

public class CourseDependencyMock extends AbstractCourseDependencyFactory {
	private final IGlobalFactoryProvider provider;

	public CourseDependencyMock(IGlobalFactoryProvider provider) {
		this.provider = provider;
	}

	@Override
	public ICoursePersistence getCoursePersistence() {
		return new CourseDBMock();
	}

	@Override
	public ICourseUserRelationshipPersistence getCourseUserRelationshipPersistence() {
		return new CourseUserRelationshipDBMock();
	}

	@Override
	public IStudentCSVParser getStudentCSVParser() {
		return null;
	}

	@Override
	public ICourseUserRelationship getCourseUserRelationship() {
		return new CourseUserRelationshipMock();
	}

	@Override
	public IStudentCsvImport getStudentCsvImport() {
		return null;
	}
}
