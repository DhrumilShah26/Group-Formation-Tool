package CSCI5308.GroupFormationTool.SurveyTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.QuestionManager.Question;
import CSCI5308.GroupFormationTool.Survey.ISurveyQuestionPersistence;
import CSCI5308.GroupFormationTool.Survey.SurveyQuestion;

public class SurveyQuestionTest {
	static IGlobalFactoryProvider provider;
	static ISurveyQuestionPersistence surveyQuestionDB;

	@BeforeAll
	static void setup() {
		provider = new GlobalDependencyMock();
		surveyQuestionDB = provider.getSurveyDependencyFactory().getSurveyQuestionPersistence();
	}

	@Test
	public void ConstructorTest() {
		List<Long> myList = new ArrayList<Long>();
		SurveyQuestion surveyQuestion = new SurveyQuestion();
		surveyQuestion.setDefault();
		assertEquals(surveyQuestion.questionIdList, myList);
	}

	@Test
	public void getQuestionBank() {
		List<Question> myTestQuestions = new ArrayList<Question>();
		myTestQuestions.add(Question.getBuilder().setTitle("Question 1").buildQuestion());
		myTestQuestions.add(Question.getBuilder().setTitle("Question 2").buildQuestion());

		SurveyQuestion surveyQuestion = new SurveyQuestion();
		assertEquals(myTestQuestions.size(), surveyQuestion.getQuestionBank(surveyQuestionDB, "B-000000").size());
		assertNull(surveyQuestion.getQuestionBank(surveyQuestionDB, null));
		assertNull(surveyQuestion.getQuestionBank(surveyQuestionDB, ""));
	}

	@Test
	public void getQuestionList() {
		List<Long> idList = new ArrayList<Long>();
		SurveyQuestion surveyQuestion = new SurveyQuestion();
		idList.add(5l);
		idList.add(6l);
		idList.add(7l);
		surveyQuestion.setQuestionList(idList);
		assertEquals(3, surveyQuestion.getQuestionList().size());
	}

	@Test
	public void setQuestionList() {
		List<Long> idList = new ArrayList<Long>();
		SurveyQuestion surveyQuestion = new SurveyQuestion();
		idList.add(5l);
		idList.add(6l);
		surveyQuestion.setQuestionList(idList);
		assertEquals(2, surveyQuestion.getQuestionList().size());
	}

	@Test
	public void addQuestionsToSurvey() {
		List<Long> idList = new ArrayList<Long>();
		SurveyQuestion surveyQuestion = new SurveyQuestion();
		assertFalse(surveyQuestion.saveQuestionsDraft(surveyQuestionDB, idList, 5l));
		idList.add(4l);
		assertFalse(surveyQuestion.saveQuestionsDraft(surveyQuestionDB, idList, -1l));
		assertTrue(surveyQuestion.saveQuestionsDraft(surveyQuestionDB, idList, 7l));
	}

	@Test
	public void fetchSurveyQuestions() {
		List<Long> idList = new ArrayList<Long>();
		idList.add(9l);
		idList.add(10l);
		idList.add(11l);
		SurveyQuestion surveyQuestion = new SurveyQuestion();

		assertEquals(idList.size(), surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, 5l).size());
		assertNull(surveyQuestion.fetchSurveyQuestions(surveyQuestionDB, 0l));
	}

}
