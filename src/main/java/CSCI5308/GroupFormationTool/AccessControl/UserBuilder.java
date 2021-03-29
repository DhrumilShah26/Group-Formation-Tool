package CSCI5308.GroupFormationTool.AccessControl;

public class UserBuilder implements IUserBuilder
{
    private long id;
    private String password;
    private String bannerId;
    private String firstName;
    private String lastName;
    private String email;

    public UserBuilder()
    {
        setDefaults();
    }

    public void setDefaults()
    {
        id = -1;
        password = "";
        bannerId = "";
        firstName = "";
        lastName = "";
        email = "";
    }

    @Override
    public IUserBuilder setId(long id)
    {
        this.id = id;
        return this;
    }

    @Override
    public IUserBuilder setBannerId(String bannerId)
    {
        this.bannerId = bannerId;
        return this;
    }

    @Override
    public IUserBuilder setPassword(String password)
    {
        this.password = password;
        return this;
    }

    @Override
    public IUserBuilder setFirstName(String firstName)
    {
        this.firstName = firstName;
        return this;
    }

    @Override
    public IUserBuilder setLastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }

    @Override
    public IUserBuilder setEmail(String email)
    {
        this.email = email;
        return this;
    }

    @Override
    public User build()
    {
        User u = new User();
        u.setID(id);
        u.setPassword(password);
        u.setEmail(email);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setBannerID(bannerId);
        return u;
    }
}
