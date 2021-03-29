package CSCI5308.GroupFormationTool.AccessControl;

public interface ICurrentUser<T> {
    public T getCurrentAuthenticatedUser();
}
