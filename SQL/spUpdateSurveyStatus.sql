DELIMITER $$

DROP PROCEDURE IF EXISTS spUpdateSurveyStatus $$

CREATE PROCEDURE spUpdateSurveyStatus (
	IN statusType VARCHAR(255),
	IN surveyID BIGINT
)
BEGIN
	SET @statusID = (SELECT statusID from SurveyStatus WHERE SurveyStatus.statusType = statusType);
	UPDATE Survey SET status = @statusID WHERE Survey.surveyID = surveyID;
END $$

DELIMITER ;