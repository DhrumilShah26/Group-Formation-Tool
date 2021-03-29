package CSCI5308.GroupFormationTool.Survey;

public enum SurveyQuestionType {

	MCQONE {
		public String toString() {
			return "MCQOne";
		}
	},
	MCQMULTIPLE{
		public String toString()
		{
			return "MCQMULTI";
		}
	},
	NUMERIC {
		public String toString() {
			return "NUMERIC";
		}
	},
	TEXT {
		public String toString() {
			return "TEXT";
		}
	}
}
