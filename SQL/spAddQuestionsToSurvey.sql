DELIMITER $$

DROP PROCEDURE IF EXISTS spAddQuestionsToSurvey $$

CREATE PROCEDURE spAddQuestionsToSurvey (
	IN surveyID BIGINT,
    IN questionID BIGINT
)
BEGIN
	DELETE FROM SurveyQuestions WHERE SurveyQuestions.surveyID = surveyID;
	INSERT INTO SurveyQuestions values (surveyID, questionID);
END $$

DELIMITER ;