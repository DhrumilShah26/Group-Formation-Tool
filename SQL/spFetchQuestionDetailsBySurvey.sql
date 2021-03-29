DELIMITER $$

DROP PROCEDURE IF EXISTS spFetchQuestionDetailsBySurvey $$

CREATE PROCEDURE spFetchQuestionDetailsBySurvey (
	IN courseID BIGINT
)
BEGIN
select s.surveyID, q.id, qt.text,qtp.type
from Question q
inner JOIN QuestionText qt on q.id = qt.id 
inner JOIN QuestionType qtp on q.type = qtp.id 
inner join SurveyQuestions sq on sq.questionID = q.id
inner join Survey s on s.surveyID = sq.surveyID
where s.courseId = courseID;

END $$

DELIMITER ;