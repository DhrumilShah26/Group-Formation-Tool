package CSCI5308.GroupFormationTool.PasswordValidationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.PasswordValidation.PasswordHistoryValidator;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import CSCI5308.GroupFormationTool.PasswordValidation.IPasswordValidatorPersistence;

@SpringBootTest
class PasswordHistoryValidatorTest 
{
	User user= new User();
	IPasswordValidatorPersistence validatorDB;
	IPasswordEncryption passwordEncryption;
	PasswordHistoryValidator passwordHistoryValidator;

	@BeforeEach
	void setUp() {
		IGlobalFactoryProvider provider = new GlobalDependencyMock();
		validatorDB = provider.getPasswordValidatorDependencyFactory().getPasswordValidatorPersistence();
		passwordEncryption = provider.getSecurityFactory().getPasswordEncryption();
		passwordHistoryValidator = new PasswordHistoryValidator("3",user,validatorDB,passwordEncryption);
	}

	@Test
	public void isValid() 
	{
		user.setBannerID("B000000");
		IPasswordValidatorPersistence validatorDB = new PasswordValidatorDBMock();
		int historyCount=3;
		String pass="pass";
		assertThat(passwordHistoryValidator.isValid("pass")).isTrue();
	}

	@Test
	public void isValidFails()
	{
		user.setBannerID("B1234567");
		IPasswordValidatorPersistence validatorDB = new PasswordValidatorDBMock();
		int historyCount=3;
		assertThat(passwordHistoryValidator.isValid("fail")).isFalse();
	}

	@Test
	public void getValidatorName()
	{
		String validatorName= passwordHistoryValidator.getValidatorName();
		assertFalse(Strings.isNullOrEmpty(validatorName));
	}
}
