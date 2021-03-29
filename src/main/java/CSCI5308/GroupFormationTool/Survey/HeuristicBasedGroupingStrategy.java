package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HeuristicBasedGroupingStrategy extends AbstractGroupingStrategy {
	private List<Rule> rules;
	private List<SurveyResponse> responses;
	private List<Integer> groupedUserIds;
	private List<Integer> usedUserIds;

	public HeuristicBasedGroupingStrategy() {
	}

	public void setDefaults() {
		groupedUserIds = new ArrayList<>();
		usedUserIds = new ArrayList<>();
	}

	@Override
	public List<Group> makeGroups(Integer numberOfGroups, List<SurveyResponse> responses, List<Rule> rules) {
		List<Group> groups = new ArrayList<>();
		if (responses.size() == 0) {
			return groups;
		}
		this.rules = rules;
		this.responses = responses;

		setDefaults();

		Integer groupSize = (int) Math.ceil(responses.size() / Double.valueOf(numberOfGroups));

		makeIndices(responses);

		while (usedUserIds.size() < responses.size()) {
			Group bestGroup = sampleBestGroup(groupSize);
			groups.add(bestGroup);
		}

		return groups;
	}

	private Group sampleBestGroup(Integer groupSize) {
		Group bestGroup = null;
		double bestGroupScore = -999.0D;
		if (groupSize > (responses.size() - groupedUserIds.size())) {
			for (SurveyResponse response : responses) {
				bestGroup = new Group();
				bestGroup.addMember(response.getUserID());
				usedUserIds.add(response.getUserID());
			}
			return bestGroup;
		}

		for (int i = 0; i < 100; i++) {
			Group randomGroup = sampleRandomGroup(groupSize);
			Double groupScore = calculateGroupScore(randomGroup, rules, responses);
			if (groupScore > bestGroupScore) {
				bestGroupScore = groupScore;
				bestGroup = randomGroup;
			}
		}
		if (bestGroup == null) {
			return new Group();
		} else {
			groupedUserIds.addAll(bestGroup.getUserIds());
			return bestGroup;
		}
	}

	private Double calculateGroupScore(Group group, List<Rule> rules, List<SurveyResponse> responses) {
		Double score = 0.0d;
		for (Rule rule : rules) {
			if (rule.getQuestionType().equals(SurveyQuestionType.MCQONE)) {
				// Calculate Fitness
			} else if (rule.getQuestionType().equals(SurveyQuestionType.MCQMULTIPLE)) {
				// Calculate Fitness
			} else if (rule.getQuestionType().equals(SurveyQuestionType.NUMERIC)) {
				StandardDeviationCalculator deviationCalculator = new StandardDeviationCalculator();
				ArrayList<Double> list = new ArrayList<>();
				for (SurveyResponse response : responses) {
					List<QuestionResponse> questionResponses = response.getQuestionResponse();
					Integer userId = questionResponses.get(rule.getIdx()).getUserID();
					if (group.hasMember(userId)) {
						list.add(Double.valueOf(questionResponses.get(rule.getIdx()).getAnswer()));
					}
				}
				Double spread = deviationCalculator.calculateStandardDeviation(list);
				if (rule.getType().equals(RuleType.SIMILAR)) {
					score = score + rule.getReward() * spread;
				} else {
					score = score - rule.getReward() / spread;
				}
			} else if (rule.getQuestionType().equals(SurveyQuestionType.TEXT)) {
				TextSimilarityCalculator similarityCalculator = new TextSimilarityCalculator();
				ArrayList<String> list = new ArrayList<>();
				for (SurveyResponse response : responses) {
					List<QuestionResponse> questionResponses = response.getQuestionResponse();
					Integer userId = questionResponses.get(rule.getIdx()).getUserID();
					if (group.hasMember(userId)) {
						list.add(questionResponses.get(rule.getIdx()).getAnswer());
					}
				}
				Double similarity = similarityCalculator.calculateTextSimilarity(list);
				if (rule.getType().equals(RuleType.SIMILAR)) {
					score = score + rule.getReward() * similarity;
				} else {
					score = score - rule.getReward() / similarity;
				}
			}
		}
		return score;
	}

	private Group sampleRandomGroup(Integer groupSize) {
		Group group = new Group();
		Random r = new Random();
		while (group.getUserIds().size() < groupSize) {
			int i = r.nextInt(this.responses.size());
			SurveyResponse response = responses.get(i);
			if (groupedUserIds.contains(response.getUserID())) {
				continue;
			}
			group.addMember(response.getUserID());
		}
		return group;
	}

	private void makeIndices(List<SurveyResponse> responses) {
		Map<Integer, Integer> questionIdToIdx = new HashMap<>();
		List<QuestionResponse> response = responses.get(0).getQuestionResponse();
		for (int i = 0; i < response.size(); i++) {
			QuestionResponse questionResponse = response.get(i);
			questionIdToIdx.put(questionResponse.getQuestionID(), i);
		}

		for (Rule r : rules) {
			r.setIdx(questionIdToIdx.get(r.getQuestionId()));
		}
	}

	@Override
	public List<Group> makeGroups(Integer numberOfGroups, List<SurveyResponse> responses) {
		return null;
	}
}
