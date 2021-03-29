package CSCI5308.GroupFormationTool.QuestionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.Database.IConnectionManager;

public class QuestionDB implements IQuestionPersistence {

	private final Logger logger = LoggerFactory.getLogger(QuestionDB.class);

	final private IConnectionManager manager;

	QuestionDB(IConnectionManager manager) {
		this.manager = manager;
	}

	@Override
	public List<Question> loadQuestionsSortedByTitle(String bannerID) {
		List<Question> questionList = new ArrayList<Question>();
		CallStoredProcedure proc = null;
		try {
			logger.info("Loading questions sorted by Date for " + bannerID);
			proc = new CallStoredProcedure("spFindSortedQuestionsByTitle(?)", manager);
			proc.setParameter(1, bannerID);
			ResultSet results = proc.executeWithResults();
			Question question;

			if (null != results) {
				logger.info("Loading questions sorted by title");
				while (results.next()) {
					long id = results.getLong(1);
					String title = results.getString(2);
					String text = results.getString(3);
					QuestionType type = QuestionType.valueOf(results.getString(4).toUpperCase());
					Timestamp timestamp = results.getTimestamp(5);

					question = new Question.QuestionBuild().setId(id).setTitle(title).setText(text).setType(type)
							.setTimestamp(timestamp).buildQuestion();
					logger.info("Loading Question... | ID: " + id + " | Title: " + title);
					questionList.add(question);
				}
			}
			logger.info("Successfully loaded questions sorted by title for " + bannerID);
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error: " + e.getMessage());
			throw new RuntimeSQLException(e);
		} catch (Exception exc) {
			System.out.println(exc);
			logger.error("Unable to load questions: " + exc.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return questionList;
	}

	@Override
	public List<Question> loadSortedQuestionsSortedByDate(String bannerID) {
		List<Question> questionList = new ArrayList<Question>();
		CallStoredProcedure proc = null;
		try {
			logger.info("Loading questions sorted by Date for " + bannerID);
			proc = new CallStoredProcedure("spFindSortedQuestionsByDate(?)", manager);
			proc.setParameter(1, bannerID);
			ResultSet results = proc.executeWithResults();
			Question question;

			if (null != results) {
				while (results.next()) {
					long id = results.getLong(1);
					String title = results.getString(2);
					String text = results.getString(3);
					QuestionType type = QuestionType.valueOf(results.getString(4).toUpperCase());
					Timestamp timestamp = results.getTimestamp(5);

					question = new Question.QuestionBuild().setId(id).setTitle(title).setText(text).setType(type)
							.setTimestamp(timestamp).buildQuestion();
					logger.info("Loading Question... | ID: " + id + " | Timestamp: " + timestamp);
					questionList.add(question);
				}
			}

			logger.info("Successfully loaded questions sorted by Date for " + bannerID);
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error: " + e.getMessage());
			throw new RuntimeSQLException(e);
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Unable to load questions: " + e.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return questionList;
	}

	@Override
	public boolean deleteQuestionByQuestionId(long questionID) {
		CallStoredProcedure proc = null;
		try {
			logger.info("Deleting question with ID - " + questionID);
			proc = new CallStoredProcedure("spDeleteQuestionsByQuestionID(?)", manager);
			proc.setParameter(1, questionID);
			proc.execute();
			logger.info("Successfully deleted question with ID - " + questionID);
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error: " + e.getMessage());
			return false;
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Unable to delete question with id - " + questionID + ": " + e.getMessage());
			return false;
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return true;
	}

	@Override
	public long createQuestion(Question question, String bannerID) {
		long id = -1;
		CallStoredProcedure proc = null;
		try {
			logger.info("Creating question for Banner ID - " + bannerID);
			proc = new CallStoredProcedure("spCreateQuestion(?,?,?,?)", manager);
			proc.setParameter(1, question.getTitle());
			proc.setParameter(2, question.getText());
			proc.setParameter(3, question.getType().toString());
			proc.setParameter(4, bannerID);
			ResultSet results = proc.executeWithResults();

			if (null != results) {
				while (results.next()) {
					id = results.getLong(1);
				}
			}
			logger.info("Successfully created question for " + bannerID + " with ID - " + id);
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error: " + e.getMessage());
			throw new RuntimeSQLException(e);
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Unable to create question for Banner ID - " + bannerID + ": " + e.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return id;
	}

	@Override
	public boolean createQuestionOption(OptionValue option, int order, long questionID) {
		CallStoredProcedure proc = null;
		try {
			logger.info("Creating option(s) for question with ID - " + questionID);
			proc = new CallStoredProcedure("spCreateOptions(?,?,?,?)", manager);
			proc.setParameter(1, questionID);
			proc.setParameter(2, option.getText());
			proc.setParameter(3, option.getStoredAs());
			proc.setParameter(4, order);
			proc.execute();
			logger.info("Option(s) successfully added for Question with ID - " + questionID);

		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error: " + e.getMessage());
			throw new RuntimeSQLException(e);
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Unable to create option for question with ID - " + questionID + ": " + e.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return true;
	}

}
