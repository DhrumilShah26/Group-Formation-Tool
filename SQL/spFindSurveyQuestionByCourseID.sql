DELIMITER $$

DROP PROCEDURE IF EXISTS spFindSurveyByCourseID $$

CREATE PROCEDURE spFindSurveyByCourseID (
	IN courseID BIGINT
)
BEGIN
	SELECT *
	FROM Survey
    WHERE Survey.courseID = courseID;
    
    SET @surveyID = (SELECT surveyID FROM Survey WHERE Survey.courseID = courseID);
    
    SET @questionID = (SELECT questionID from surveyquestions where surveyquestions.surveyID = @surveyID);
    
    SET @questionText = (SELECT text from questiontext where questiontext.questionID in( SELECT questionID from surveyquestions where surveyquestions.surveyID = @surveyID));
    
    SET @questionType = (SELECT type from questiontype where questiontype.questionID in (SELECT questionID from surveyquestions where surveyquestions.surveyID = @surveyID));
    
    SELECT @surveyID, @questionID, @questionText, @questionType ;
END $$

DELIMITER ;