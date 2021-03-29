package CSCI5308.GroupFormationTool.AccessControl;

public interface IUserBuilder
{
    static IUserBuilder getDefaultBuilder() {
        return new UserBuilder();
    }

    IUserBuilder setId(long id);
    IUserBuilder setBannerId(String bannerId);
    IUserBuilder setPassword(String password);
    IUserBuilder setFirstName(String firstName);
    IUserBuilder setLastName(String lastName);
    IUserBuilder setEmail(String email);
    User build();
}
