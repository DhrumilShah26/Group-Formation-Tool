package CSCI5308.GroupFormationTool.QuestionManagerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.Question;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionType;

@SuppressWarnings("deprecation")
public class QuestionTest {

	static IGlobalFactoryProvider provider;
	static IQuestionPersistence questionDB;

	@BeforeAll
	static void setup() {
		provider = new GlobalDependencyMock();
		questionDB = provider.getQuestionDependencyFactory().getQuestionPersistence();
	}

	@Test
	public void ConstructorTests() {
		Question q = new Question.QuestionBuild().buildQuestion();
		q.setDefaults();
		Assert.isTrue(q.getTitle().isEmpty());
		Assert.isTrue(q.getText().isEmpty());
		Assert.isNull(q.getType());
		Assert.isNull(q.getTimestamp());
	}

	@Test
	public void getTimestamp() {
		Timestamp time = Timestamp.valueOf("2020-06-16 00:00:00");
		Question q = new Question.QuestionBuild().setTimestamp(time).buildQuestion();
		assertEquals(time, q.getTimestamp());
	}

	@Test
	public void setTimestamp() {
		Timestamp time = Timestamp.valueOf("2020-06-16 00:00:00");
		Question q = new Question.QuestionBuild().setTimestamp(time).buildQuestion();
		assertEquals(time, q.getTimestamp());
	}

	@Test
	public void getId() {
		Question q = new Question.QuestionBuild().setId(7).buildQuestion();
		assertEquals(q.getId(), 7);
	}

	@Test
	public void setId() {
		Question q = new Question.QuestionBuild().setId(7).buildQuestion();
		assertEquals(q.getId(), 7);
	}

	@Test
	public void getTitle() {
		Question q = new Question.QuestionBuild().setTitle("Test title").buildQuestion();
		assertEquals("Test title", q.getTitle());
	}

	@Test
	public void setTitle() {
		Question q = new Question.QuestionBuild().setTitle("Test title").buildQuestion();
		assertEquals("Test title", q.getTitle());
	}

	@Test
	public void getText() {
		Question q = new Question.QuestionBuild().setText("Test text").buildQuestion();
		assertEquals("Test text", q.getText());
	}

	@Test
	public void setText() {
		Question q = new Question.QuestionBuild().setText("Test text").buildQuestion();
		assertEquals("Test text", q.getText());
	}

	@Test
	public void getType() {
		Question q = new Question.QuestionBuild().setType(QuestionType.TEXT).buildQuestion();
		assertEquals(q.getType(), QuestionType.TEXT);
		assertEquals("Text", QuestionType.TEXT.toString());
		assertEquals("MCQOne", QuestionType.MCQONE.toString());
		assertEquals("MCQMultiple", QuestionType.MCQMULTIPLE.toString());
		assertEquals("Numeric", QuestionType.NUMERIC.toString());
	}

	@Test
	public void setType() {
		Question q = new Question.QuestionBuild().setType(QuestionType.TEXT).buildQuestion();
		assertEquals(q.getType(), QuestionType.TEXT);
	}

	@Test
	public void deleteQuestion() {
		Question q = new Question.QuestionBuild().buildQuestion();
		IQuestionPersistence questionDB = new QuestionDBMock();
		q.setDefaults();
		boolean deleteCheck = q.deleteQuestion(questionDB, q.getId());
		assertFalse(deleteCheck);
		boolean status = questionDB.deleteQuestionByQuestionId(q.getId());
		assertFalse(status);

		Question q1 = new Question.QuestionBuild().setId(1).setTitle("Test Title").setText("Test Question")
				.setType(QuestionType.TEXT).setTimestamp(Timestamp.valueOf("2020-06-16 00:00:00")).buildQuestion();
		assertTrue(q1.deleteQuestion(questionDB, q1.getId()));
		status = questionDB.deleteQuestionByQuestionId(q1.getId());
		assertTrue(status);
	}

	@Test
	public void createQuestion() {
		Question q1 = new Question.QuestionBuild().setId(1).setTitle("Test Title").setText("Test Question")
				.setType(QuestionType.TEXT).setTimestamp(Timestamp.valueOf("2020-06-16 00:00:00")).buildQuestion();
		String bannerId = "B-000000";
		Long qIdLong = q1.createQuestion(questionDB, bannerId);
		assertEquals(1, qIdLong);

		Question q = new Question.QuestionBuild().buildQuestion();
		q.setDefaults();
		qIdLong = q.createQuestion(questionDB, bannerId);
		assertEquals(0, qIdLong);
	}

	@Test
	public void loadQuestionsSortedByTitle() {
		List<Question> sortedQuestions = new ArrayList<>();
		List<String> sortList = new ArrayList<>();
		List<String> beforeSortList = new ArrayList<>();
		String bannerId = "B-000000";
		sortedQuestions = questionDB.loadQuestionsSortedByTitle(bannerId);
		for (Question question : sortedQuestions) {
			sortList.add(question.getTitle());
		}
		beforeSortList.addAll(sortList);
		Collections.sort(sortList);
		assertEquals(sortList, beforeSortList);

		assertThrows(RuntimeSQLException.class, () -> questionDB.loadQuestionsSortedByTitle("B-DB_CONNECTION_EX"));

		assertThrows(NullPointerException.class, () -> questionDB.loadQuestionsSortedByTitle("B-NULL_PTR_EX"));

	}

	@Test
	public void loadSortedQuestionsSortedByDate() {
		List<Question> sortedQuestions = new ArrayList<>();
		String bannerId = "B-000000";
		sortedQuestions = questionDB.loadSortedQuestionsSortedByDate(bannerId);
		Timestamp firstItem = sortedQuestions.get(0).getTimestamp();
		Timestamp secondItem = sortedQuestions.get(1).getTimestamp();
		// Checking for ascending order of Timestamps
		assertEquals(secondItem.after(firstItem), true);

		assertThrows(RuntimeSQLException.class, () -> questionDB.loadSortedQuestionsSortedByDate("B-DB_CONNECTION_EX"));
		assertThrows(NullPointerException.class, () -> questionDB.loadSortedQuestionsSortedByDate("B-NULL_PTR_EX"));
	}

	@Test
	public void getBuilder() {
		Question question = Question.getBuilder().setId(1).setTitle("Hello").buildQuestion();
		assertEquals(1, question.getId());
		assertEquals("Hello", question.getTitle());
	}
}
