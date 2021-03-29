DELIMITER $$

DROP PROCEDURE IF EXISTS spFindSurveyByCourse $$

CREATE PROCEDURE spFindSurveyByCourse (
	IN courseID BIGINT
)
BEGIN
	
    SET @surveyID = (SELECT surveyID FROM Survey WHERE Survey.courseID = courseID);
    
    SET @title = (SELECT title FROM Survey WHERE Survey.courseID = courseID);
    
    SET @userID = (SELECT userID FROM Survey WHERE Survey.courseID = courseID);
    
    SET @statusID = (SELECT status FROM Survey WHERE Survey.courseID = courseID);
    
    SET @statusType = (SELECT statusType from SurveyStatus where SurveyStatus.statusID = @statusID);
    
    SET @bannerID = (SELECT bannerID from User where User.id = @userID);
    
    SELECT @bannerID, @statusType, @title, @surveyID;
END $$

DELIMITER ;