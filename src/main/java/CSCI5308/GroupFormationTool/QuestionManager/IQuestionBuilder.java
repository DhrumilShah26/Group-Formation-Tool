package CSCI5308.GroupFormationTool.QuestionManager;

import java.sql.Timestamp;

import CSCI5308.GroupFormationTool.QuestionManager.Question.QuestionBuild;

public interface IQuestionBuilder {
	public QuestionBuild setId(long id);

	public QuestionBuild setTitle(String title);

	public QuestionBuild setText(String text);

	public QuestionBuild setType(QuestionType type);

	public QuestionBuild setTimestamp(Timestamp timestamp);
}
