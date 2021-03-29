package CSCI5308.GroupFormationTool.QuestionManagerTest;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionPersistence;
import CSCI5308.GroupFormationTool.QuestionManager.OptionValue;
import CSCI5308.GroupFormationTool.QuestionManager.Question;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionType;

public class QuestionDBMock implements IQuestionPersistence {
	List<Question> questions;

	@Override
	public List<Question> loadQuestionsSortedByTitle(String bannerId) {
		questions = new ArrayList<Question>();

		if (bannerId.equalsIgnoreCase("B-DB_CONNECTION_EX")) {
			throw new RuntimeSQLException(new SQLException());
		} else if (bannerId.equalsIgnoreCase("B-NULL_PTR_EX")) {
			throw new NullPointerException();
		} else {
			if (bannerId.equals("B-000000")) {
				Question q = new Question.QuestionBuild().setId(1).setTitle("Test Title").setText("Test Question")
						.setType(QuestionType.TEXT).setTimestamp(Timestamp.valueOf("2020-06-16 00:00:00"))
						.buildQuestion();
				questions.add(q);

				q = new Question.QuestionBuild().setId(1).setTitle("Test Title 2").setText("Test Question")
						.setType(QuestionType.TEXT).setTimestamp(Timestamp.valueOf("2020-06-17 00:00:00"))
						.buildQuestion();
				questions.add(q);
			}
		}
		return questions;
	}

	@Override
	public List<Question> loadSortedQuestionsSortedByDate(String bannerId) {
		questions = new ArrayList<Question>();
		if (bannerId.equalsIgnoreCase("B-DB_CONNECTION_EX")) {
			throw new RuntimeSQLException(new SQLException());
		} else if (bannerId.equalsIgnoreCase("B-NULL_PTR_EX")) {
			throw new NullPointerException();
		} else {
			if (bannerId.equals("B-000000")) {
				Question q = new Question.QuestionBuild().setId(1).setTitle("Test Title 2").setText("Test Question")
						.setType(QuestionType.TEXT).setTimestamp(Timestamp.valueOf("2020-06-16 00:00:00"))
						.buildQuestion();
				questions.add(q);

				q = new Question.QuestionBuild().setId(1).setTitle("Test Title").setText("Test Question")
						.setType(QuestionType.TEXT).setTimestamp(Timestamp.valueOf("2020-06-17 00:00:00"))
						.buildQuestion();
				questions.add(q);
			}
		}

		return questions;
	}

	@Override
	public boolean deleteQuestionByQuestionId(long questionId) {
		Question q = new Question.QuestionBuild().setId(1).setTitle("Test Title").setText("Test Question")
				.setType(QuestionType.TEXT).buildQuestion();

		if (questionId > -1) {
			q.setDefaults();
			return true;
		}
		return false;
	}

	@Override
	public long createQuestion(Question question, String bannerID) {
		if (bannerID.equalsIgnoreCase("B-DB_CONNECTION_EX")) {
			return -2;
		}
		if (question.getId() > -1) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean createQuestionOption(OptionValue option, int order, long questionID) {
		if (questionID == -2) {
			throw new RuntimeSQLException(new SQLException());
		}
		if (questionID == -1 || isStringEmpty(option.getText()) || isStringEmpty(option.getStoredAs())) {
			return false;
		}
		return true;
	}

	public boolean isStringEmpty(String s) {
		return s.replaceAll(" ", "").length() == 0;
	}

}
