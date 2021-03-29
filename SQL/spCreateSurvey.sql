DELIMITER $$

DROP PROCEDURE IF EXISTS spCreateSurvey $$

CREATE PROCEDURE spCreateSurvey(
IN surveyTitle VARCHAR(255),
IN courseID BIGINT,
IN statusType VARCHAR(255),
IN bannerID VARCHAR(20)
)
BEGIN

	SET@userID = (SELECT id from User
					WHERE User.bannerID= bannerID);
	SET @statusID = (SELECT statusID from SurveyStatus WHERE SurveyStatus.statusType = statusType);
	SET @surveyID = LAST_INSERT_ID();
	INSERT into Survey (title, courseID, status, userID) values (surveyTitle, courseID, @statusID, @userID);
	
	SELECT @surveyID;

	
    
END$$
DELIMITER ;
