package CSCI5308.GroupFormationTool.Survey;

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
import CSCI5308.GroupFormationTool.QuestionManager.Question;
import CSCI5308.GroupFormationTool.QuestionManager.QuestionType;

public class SurveyQuestionDB implements ISurveyQuestionPersistence {
	private final Logger logger = LoggerFactory.getLogger(SurveyQuestionDB.class);

	final private IConnectionManager manager;

	SurveyQuestionDB(IConnectionManager manager) {
		this.manager = manager;
	}

	@Override
	public List<Question> getQuestionBank(String bannerID) {
		List<Question> questionBank = new ArrayList<Question>();
		CallStoredProcedure proc = null;
		Question question;
		try {
			logger.info("Loading questions for Survey Question Bank for " + bannerID);
			proc = new CallStoredProcedure("spFindSortedQuestionsByTitle(?)", manager);
			proc.setParameter(1, bannerID);
			ResultSet results = proc.executeWithResults();
			if (results != null) {
				while (results.next()) {
					long id = results.getLong(1);
					String title = results.getString(2);
					String text = results.getString(3);
					QuestionType type = QuestionType.valueOf(results.getString(4).toUpperCase());
					Timestamp timestamp = results.getTimestamp(5);

					question = Question.getBuilder().setId(id).setTitle(title).setText(text).setType(type)
							.setTimestamp(timestamp).buildQuestion();
					logger.info("Loading Question for Survey Question Bank... | ID: " + id + " | Title: " + title);
					questionBank.add(question);
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error - Fetching Question Bank: " + e.getMessage());
			throw new RuntimeSQLException(e);
		} catch (Exception exc) {
			System.out.println(exc);
			logger.error("Unable to load questions: " + exc.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}

		return questionBank;
	}

	@Override
	public boolean addQuestionsToSurvey(List<Long> questionIdList, Long surveyId) {
		CallStoredProcedure proc = null;
		CallStoredProcedure procDel = null;

		try {
			logger.info("Loading questions for Survey Question Bank for " + surveyId);
			procDel = new CallStoredProcedure("spRemoveQuestionsFromSurvey(?)", manager);
			procDel.setParameter(1, surveyId);
			procDel.execute();
			proc = new CallStoredProcedure("spAddQuestionsToSurvey(?, ?)", manager);

			for (Long questionID : questionIdList) {
				proc.setParameter(1, surveyId);
				proc.setParameter(2, questionID);
				proc.execute();
			}
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error - Adding Questions to Survey: " + e.getMessage());
			return false;
		} catch (Exception exc) {
			System.out.println(exc);
			logger.error("Unable to load questions: " + exc.getMessage());
			return false;
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
			if (null != procDel) {
				proc.cleanup();
			}
		}
		return true;
	}

	@Override
	public List<Long> fetchQuestionBySurvey(Long surveyId) {
		List<Long> questionIdList = new ArrayList<Long>();
		CallStoredProcedure proc = null;

		try {
			logger.info("Loading questions for Survey Question Bank for " + surveyId);
			proc = new CallStoredProcedure("spFetchQuestionBySurvey(?)", manager);

			proc.setParameter(1, surveyId);
			ResultSet resultSet = proc.executeWithResults();

			if (resultSet != null) {
				while (resultSet.next()) {
					questionIdList.add(resultSet.getLong("questionID"));
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error - Fetching Survey Questions: " + e.getMessage());
			throw new RuntimeSQLException(e);
		} catch (Exception exc) {
			System.out.println(exc);
			logger.error("Unable to load questions: " + exc.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}

		return questionIdList;
	}

}
