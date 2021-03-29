DELIMITER $$

DROP PROCEDURE IF EXISTS spRemoveQuestionsFromSurvey $$

CREATE PROCEDURE spRemoveQuestionsFromSurvey (
	IN surveyID BIGINT
)
BEGIN
	DELETE from SurveyQuestions WHERE SurveyQuestions.surveyID = surveyID;
END $$

DELIMITER ;