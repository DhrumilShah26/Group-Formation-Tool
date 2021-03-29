package CSCI5308.GroupFormationTool.QuestionManager;

import java.sql.Timestamp;

public class Question {
	private long id;
	private String title;
	private String text;
	private QuestionType type;
	private Timestamp timestamp;

	private Question() {
	}

	public void setDefaults() {
		id = -1;
		title = "";
		text = "";
		type = null;
		timestamp = null;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public QuestionType getType() {
		return type;
	}

	public boolean deleteQuestion(IQuestionPersistence questionDB, long questionID) {
		return questionDB.deleteQuestionByQuestionId(questionID);
	}

	public long createQuestion(IQuestionPersistence questionDB, String bannerID) {
		return questionDB.createQuestion(this, bannerID);
	}

	public static IQuestionBuilder getBuilder() {
		return new QuestionBuild();
	}

	public static class QuestionBuild implements IQuestionBuilder {

		public QuestionBuild()
		{
		}

		private long id;
		private String title;
		private String text;
		private QuestionType type;
		private Timestamp timestamp;

		@Override
		public QuestionBuild setId(long id) {
			this.id = id;
			return this;
		}

		@Override
		public QuestionBuild setTitle(String title) {
			this.title = title;
			return this;

		}

		@Override
		public QuestionBuild setText(String text) {
			this.text = text;
			return this;

		}

		@Override
		public QuestionBuild setType(QuestionType type) {
			this.type = type;
			return this;
		}

		@Override
		public QuestionBuild setTimestamp(Timestamp timestamp) {
			this.timestamp = timestamp;
			return this;

		}

		public Question buildQuestion() {
			Question question = new Question();
			question.id = this.id;
			question.title = this.title;
			question.text = this.text;
			question.type = this.type;
			question.timestamp = this.timestamp;
			return question;
		}

	}

}
