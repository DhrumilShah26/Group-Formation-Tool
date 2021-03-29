DELIMITER $$

DROP PROCEDURE IF EXISTS spFetchQuestionOptionByQuestionID $$

CREATE PROCEDURE spFetchQuestionOptionByQuestionID (
	IN courseID BIGINT
)
BEGIN
select q.id,qc.displayText,qc.storedAs from Question q
INNER JOIN QuestionOption qo on q.id = qo.questionID
INNER JOIN QuestionChoice qc on qo.choiceID = qc.id
inner join SurveyQuestions sq on sq.questionID = q.id
inner join Survey s on s.surveyID = sq.surveyID
where s.courseId = courseID;

END $$

DELIMITER ;