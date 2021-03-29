DELIMITER $$

DROP PROCEDURE IF EXISTS spFetchQuestionBySurvey $$

CREATE PROCEDURE spFetchQuestionBySurvey (
	IN surveyID BIGINT
)
BEGIN
	SELECT questionID from SurveyQuestions WHERE SurveyQuestions.surveyID = surveyID;
END $$

DELIMITER ;