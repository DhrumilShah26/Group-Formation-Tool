package CSCI5308.GroupFormationTool.SurveyTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.Survey.ISurveyPersistence;
import CSCI5308.GroupFormationTool.Survey.Survey;
import CSCI5308.GroupFormationTool.Survey.SurveyStatus;

public class SurveyTest {

	static IGlobalFactoryProvider provider;
	static ISurveyPersistence surveyDB;

	@BeforeAll
	static void setup() {
		provider = new GlobalDependencyMock();
		surveyDB = provider.getSurveyDependencyFactory().getSurveyPersistence();
	}

	@Test
	public void getSurveyID() {
		Long surveyID = 7l;
		Survey survey = Survey.getSurveyBuilder().setSurveyID(7).buildSurvey();

		assertEquals(survey.getSurveyId(), surveyID);
	}

	@Test
	public void getSurveyTitle() {
		String surveyTitle = "Test Getter";
		Survey survey = Survey.getSurveyBuilder().setSurveyTitle("Test Getter").buildSurvey();

		assertEquals(survey.getSurveyTitle(), surveyTitle);
	}

	@Test
	public void getUserBannerID() {
		String bannerID = "B00851387";
		Survey survey = Survey.getSurveyBuilder().setUserBannerID("B00851387").buildSurvey();

		assertEquals(survey.getUserBannerId(), bannerID);
	}

	@Test
	public void getSurveyStatus() {
		String status = "DRAFT";
		Survey survey = Survey.getSurveyBuilder().setSurveyStatus(SurveyStatus.DRAFT).buildSurvey();

		assertEquals(survey.getStatus(), SurveyStatus.valueOf(status));
	}

	@Test
	public void setStatus() {
		Survey survey = Survey.getSurveyBuilder().setSurveyStatus(SurveyStatus.DRAFT).buildSurvey();
		assertEquals("Draft", survey.getStatus().toString());
	}

	@Test
	public void setSurveyID() {
		Survey survey = Survey.getSurveyBuilder().setSurveyID(7).buildSurvey();
		assertEquals(7l, survey.getSurveyId());
	}

	@Test
	public void setSurveyTitle() {
		Survey survey = Survey.getSurveyBuilder().setSurveyTitle("Advanced Survey").buildSurvey();
		assertEquals("Advanced Survey", survey.getSurveyTitle());
	}

	@Test
	public void setUserBannerID() {
		Survey survey = Survey.getSurveyBuilder().setUserBannerID("B00XXXX").buildSurvey();
		assertEquals("B00XXXX", survey.getUserBannerId());
	}

	@Test
	public void createSurvey() {
		Survey survey = Survey.getSurveyBuilder().setSurveyID(1).setUserBannerID("B-000000").setSurveyTitle("mysurvey")
				.setSurveyStatus(SurveyStatus.DRAFT).buildSurvey();

		Long surveyID = survey.createSurvey(surveyDB, 3);

		assertEquals(surveyID, 1l);
	}

	@Test
	public void getSurveyByCourse() {
		Survey survey = surveyDB.getSurveyByCourse(3);
		Long surveyID = survey.getSurveyId();

		survey = survey.getSurveyByCourse(surveyDB, 5);
		Long surveyIDFromModel = survey.getSurveyId();

		assertEquals(surveyID, surveyIDFromModel);

		survey = survey.getSurveyByCourse(surveyDB, -1);
		assertNull(survey);
	}

	@Test
	public void updateSurveyStatus() {
		Survey survey = Survey.getSurveyBuilder().setSurveyID(5).setSurveyStatus(SurveyStatus.DRAFT).buildSurvey();
		Boolean testStatus = survey.updateSurveyStatus(surveyDB, "Not Valid", survey.getSurveyId());
		assertFalse(testStatus);

		testStatus = survey.updateSurveyStatus(surveyDB, "Published", survey.getSurveyId());
		assertTrue(testStatus);

		testStatus = survey.updateSurveyStatus(surveyDB, "Published", -1l);
		assertFalse(testStatus);
	}

	@Test
	public void getBuilder() {
		Survey survey = Survey.getSurveyBuilder().setSurveyID(5).setSurveyStatus(SurveyStatus.DRAFT)
				.setSurveyTitle("My Title").setUserBannerID("B-000000").buildSurvey();

		assertEquals(5, survey.getSurveyId());
		assertEquals("My Title", survey.getSurveyTitle());
		assertEquals("B-000000", survey.getUserBannerId());
		assertEquals("Draft", survey.getStatus().toString());
	}

}
