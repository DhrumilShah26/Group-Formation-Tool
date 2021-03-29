package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.*;

import static org.assertj.core.api.Assertions.assertThat;

import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.PasswordValidation.IPasswordValidatorEnumerator;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;

@SpringBootTest
@SuppressWarnings("deprecation")
public class UserTest
{
	static IGlobalFactoryProvider provider;
	static IUserPersistence persistence;
	static IPasswordEncryption passwordEncryption;
	static IUserNotifications userNotifications;
	static IPasswordValidatorEnumerator pve;
	static AbstractAclDependencyFactory factory;

	@BeforeAll
	static void setup() {
		provider = new GlobalDependencyMock();
		persistence = provider.getAclDependencyFactory().getUserPersistence();
		passwordEncryption = provider.getSecurityFactory().getPasswordEncryption();
		userNotifications = new NotificationMock();
		pve = provider.getPasswordValidatorDependencyFactory().getPasswordValidatorEnumerator();
		factory = provider.getAclDependencyFactory();
	}

	@Test
	public void ConstructorTests()
	{
		User u = factory.makeUserBuilder().build();
		Assert.isTrue(u.getBannerID().isEmpty());
		Assert.isTrue(u.getFirstName().isEmpty());
		Assert.isTrue(u.getLastName().isEmpty());
		Assert.isTrue(u.getEmail().isEmpty());

		u = new User(1, persistence);
		Assert.isTrue(u.getBannerID().equals("B00000000"));
	}
	
	@Test
	public void setIDTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setID(10);
		Assert.isTrue(10 == u.getID());
	}
	
	@Test
	public void getIDTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setID(10);
		Assert.isTrue(10 == u.getID());
	}
	
	@Test
	public void setBannerIDTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setBannerID("B00000000");
		Assert.isTrue(u.getBannerID().equals("B00000000"));
		Assert.isTrue(u.getBanner().equals("B00000000"));
	}
	
	@Test
	public void getBannerIDTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setBannerID("B00000000");
		Assert.isTrue(u.getBannerID().equals("B00000000"));
	}
	
	@Test
	public void setFirstNameTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setFirstName("Rob");
		Assert.isTrue(u.getFirstName().equals("Rob"));
	}
	
	@Test
	public void getFirstNameTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setFirstName("Rob");
		Assert.isTrue(u.getFirstName().equals("Rob"));
	}

	@Test
	public void setLastNameTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setLastName("Hawkey");
		Assert.isTrue(u.getLastName().equals("Hawkey"));
	}

	@Test
	public void getLastNameTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setLastName("Hawkey");
		Assert.isTrue(u.getLastName().equals("Hawkey"));
	}
	
	@Test
	public void setEmailTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setEmail("rhawkey@dal.ca");
		Assert.isTrue(u.getEmail().equals("rhawkey@dal.ca"));
	}
	
	@Test
	public void getEmailTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setEmail("rhawkey@dal.ca");
		Assert.isTrue(u.getEmail().equals("rhawkey@dal.ca"));
	}
	
	@Test
	public void createUser()
	{
		User user = factory.makeUserBuilder().build();
		persistence.createUser(user);
		Assert.isTrue(user.getId() == 0);
		Assert.isTrue(user.getBannerID().equals("B00000000"));
	}

	@Test
	public void isBannerIDValidTest()
	{
		Assert.isTrue(User.isBannerIDValid("B000000000"));
		assertThat(User.isBannerIDValid(null)).isFalse();
		assertThat(User.isBannerIDValid("")).isFalse();
	}
		
	@Test
	public void isFirstNameValidTest()
	{
		Assert.isTrue(User.isFirstNameValid("rob"));
		assertThat(User.isFirstNameValid(null)).isFalse();
		assertThat(User.isFirstNameValid("")).isFalse();
	}
	
	@Test
	public void isLastNameValidTest()
	{
		Assert.isTrue(User.isLastNameValid("hawkey"));
		assertThat(User.isLastNameValid(null)).isFalse();
		assertThat(User.isLastNameValid("")).isFalse();
	}
	
	@Test
	public void isEmailValidTest()
	{
		Assert.isTrue(User.isEmailValid("rhawkey@dal.ca"));
		assertThat(User.isEmailValid(null)).isFalse();
		assertThat(User.isEmailValid("")).isFalse();
		assertThat(User.isEmailValid("@dal.ca")).isFalse();
		assertThat(User.isEmailValid("rhawkey@")).isFalse();
	}

	@Test
	public void isValidUserTest()
	{
		User user = factory.makeUserBuilder().build();
		persistence.createUser(user);
		Assertions.assertTrue(user.isValidUser());
	}

	@Test
	public void getPasswordTest()
	{
		User u = factory.makeUserBuilder().build();
		u.setPassword("A");
		Assertions.assertNotNull(u.getPassword());
		Assertions.assertEquals("A", u.getPassword());
	}
	
	@Test
	public void updateUserTest() {
		User u = factory.makeUserBuilder().build();
		Assertions.assertTrue(u.updateUser(persistence));
	}

	@Test
	public void createUserTest() {
		IUserBuilder builder = factory.makeUserBuilder()
				.setBannerId("B-000000")
				.setPassword("12345");
		final User usr = builder.build();
		ArrayList<String> errors = new ArrayList<>();
		Assertions.assertThrows(UserExistsException.class, ()->
			usr.createUser(persistence, pve, passwordEncryption, userNotifications, errors)
		);
		Assertions.assertEquals(0, errors.size());

		builder.setPassword("1234");
		User u = builder.build();
		u.createUser(persistence, pve, passwordEncryption, userNotifications, errors);
		Assertions.assertEquals(1, errors.size());

		builder.setBannerId("B-8796")
				.setPassword("12345");
		u = builder.build();

		boolean res = u.createUser(persistence, pve, passwordEncryption, userNotifications, errors);
		Assertions.assertTrue(res);
	}
}
