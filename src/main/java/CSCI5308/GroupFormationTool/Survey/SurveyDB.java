package CSCI5308.GroupFormationTool.Survey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CSCI5308.GroupFormationTool.RuntimeSQLException;
import CSCI5308.GroupFormationTool.Courses.Role;
import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.Database.IConnectionManager;

public class SurveyDB implements ISurveyPersistence {
	private final Logger logger = LoggerFactory.getLogger(SurveyDB.class);

	final private IConnectionManager manager;

	SurveyDB(IConnectionManager manager) {
		this.manager = manager;
	}

	@Override
	public long createSurvey(Survey survey, long courseID) {
		long id = -1;
		String bannerID = survey.getUserBannerId();
		System.out.println("Status: " + survey.getStatus().toString());
		CallStoredProcedure proc = null;
		try {
			logger.info("Creating survey for Banner ID - " + bannerID);
			proc = new CallStoredProcedure("spCreateSurvey(?,?,?,?)", manager);
			proc.setParameter(1, survey.getSurveyTitle());
			proc.setParameter(2, courseID);
			proc.setParameter(3, survey.getStatus().toString().toUpperCase());
			proc.setParameter(4, bannerID);
			ResultSet results = proc.executeWithResults();

			if (null != results) {
				while (results.next()) {
					id = results.getLong("@surveyID");
				}
			}
			logger.info("Successfully created survey for " + bannerID + " with ID - " + id);
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error - Creating Survey: " + e.getMessage());
			throw new RuntimeSQLException(e);
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Unable to create survey for Banner ID - " + bannerID + ": " + e.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return id;
	}

	@Override
	public Survey getSurveyByCourse(long courseID) {
		CallStoredProcedure proc = null;
		Survey survey = null;
		try {
			logger.info("Finding survey for Course ID - " + courseID);
			proc = new CallStoredProcedure("spFindSurveyByCourse(?)", manager);
			proc.setParameter(1, courseID);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				results.beforeFirst();
				while (results.next()) {
					System.out.println(results.getString("@title"));
					survey = Survey.getSurveyBuilder().setSurveyID(results.getLong("@surveyID"))
							.setSurveyTitle(results.getString("@title"))
							.setSurveyStatus(SurveyStatus.valueOf(results.getString("@statusType")))
							.setUserBannerID(results.getString("@bannerID")).buildSurvey();
				}
				logger.info("Successfully fetched survey for course - " + courseID);
			} else {
				logger.warn("No Such Course - returning null survey");
			}
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error - Fetching Survey By Course ID: " + e.getMessage());
			throw new RuntimeSQLException(e);
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Unable to fetch survey for Course ID - " + courseID + ": " + e.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return survey;
	}

	@Override
	public boolean updateSurveyStatus(Long surveyID, String status) {

		CallStoredProcedure proc = null;

		try {
			logger.info("Updating status for Survey - " + surveyID);
			proc = new CallStoredProcedure("spUpdateSurveyStatus(?, ?)", manager);

			proc.setParameter(1, status);
			proc.setParameter(2, surveyID);
			proc.execute();
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error - Updating Survey Status: " + e.getMessage());
			return false;
		} catch (Exception exc) {
			System.out.println(exc);
			logger.error("Unable to update Survey Status: " + exc.getMessage());
			return false;
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return true;

	}

	@Override
	public List<QuestionDetails> getSurveyforStudent(long courseID) {
		CallStoredProcedure proc = null;

		List<QuestionDetails> questionList = new ArrayList<QuestionDetails>();
		try {
			logger.info("Finding survey question for Course ID - " + courseID);
			proc = new CallStoredProcedure("spFetchQuestionDetailsBySurvey(?)", manager);
			proc.setParameter(1, courseID);
			ResultSet results = proc.executeWithResults();

			if (null != results) {
				while (results.next()) {
					QuestionDetails questionDetails = new QuestionDetails();
					questionDetails.setSurveyID(results.getInt(1));
					questionDetails.setQuestionID(results.getInt(2));
					questionDetails.setQuestionText(results.getString(3));
					questionDetails.setQuestionType(results.getString(4));
					questionList.add(questionDetails);
				}

			}
			logger.info("Successfully fetched survey for course - " + courseID);
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error - Fetching Survey By Course ID: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Unable to fetch survey for Course ID - " + courseID + ": " + e.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return questionList;
	}

	@Override
	public List<QuestionOption> getQuestionOptionforStudent(long questionID) {
		CallStoredProcedure proc = null;

		List<QuestionOption> optionList = new ArrayList<QuestionOption>();
		try {
			
			
			
			logger.info("Finding survey question for Course ID - " + questionID);
			proc = new CallStoredProcedure("spFetchQuestionOptionByQuestionID(?)", manager);
			proc.setParameter(1, questionID);
			ResultSet results = proc.executeWithResults();
			
			

			if (null != results) {
				while (results.next()) {
					QuestionOption questionOption = new QuestionOption();
					questionOption.setId(results.getInt(1));
					questionOption.setDisplayText(results.getString(2));
					questionOption.setStoredAs(results.getString(3));
					optionList.add(questionOption);
				}

			}
			logger.info("Successfully fetched option for question - " + questionID);
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error - Fetching option By question ID: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Unable to fetch option for question ID - " + questionID + ": " + e.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return optionList;
	}

	@Override
	public List<Long> getStudentByCourse(long courseID) {
		List<Long> studentIdsList = new ArrayList<Long>();
		CallStoredProcedure proc = null;

		try {
			logger.info("Finding students for Course ID - " + courseID);
			proc = new CallStoredProcedure("spFindUsersWithCourseRole(?,?)", manager);
			proc.setParameter(1, Role.STUDENT.toString().toUpperCase());
			proc.setParameter(2, courseID);
			ResultSet results = proc.executeWithResults();

			if (null != results) {
				while (results.next()) {
					studentIdsList.add(results.getLong(1));
				}

			}
			logger.info("Successfully fetched students for course - " + courseID);
		} catch (SQLException e) {
			System.out.println(e);
			logger.error("Database Error - Fetching studentsfor course ID: " + e.getMessage());
		} catch (Exception e) {
			System.out.println(e);
			logger.error("Unable to fetch students for course ID - " + courseID + ": " + e.getMessage());
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}

		return studentIdsList;
	}

}
