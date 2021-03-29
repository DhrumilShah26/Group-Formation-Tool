package CSCI5308.GroupFormationTool.Survey;

public enum SurveyStatus {
	DRAFT {
		public String toString() {
			return "Draft";
		}
	},
	PUBLISHED {
		public String toString() {
			return "Published";
		}
	}
}
