package CSCI5308.GroupFormationTool.Survey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Group {
	private Set<Integer> userIds = new HashSet<>();

	public void addMember(Integer userId) {
		userIds.add(userId);
	}

	public boolean hasMember(Integer userId) {
		return userIds.contains(userId);
	}

	public Set<Integer> getUserIds() {
		return userIds;
	}

	public void setUserIds(Set<Integer> userIds) {
		this.userIds = userIds;
	}

	public static List<Group> makeEmptyGroups(Integer numberOfGroups) {
		List<Group> groups = new ArrayList<>();
		IntStream.range(0, numberOfGroups).forEach(i -> groups.add(new Group()));
		return groups;
	}

	@Override
	public String toString() {
		return "Group [userIds=" + userIds + "]";
	}

}
