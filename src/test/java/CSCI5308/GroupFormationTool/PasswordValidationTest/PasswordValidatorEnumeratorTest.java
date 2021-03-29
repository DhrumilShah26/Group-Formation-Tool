package CSCI5308.GroupFormationTool.PasswordValidationTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.PasswordValidation.PasswordValidatorEnumerator;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import CSCI5308.GroupFormationTool.PasswordValidation.IPasswordValidatorPersistence;
import org.springframework.security.core.parameters.P;

@SpringBootTest
class PasswordValidatorEnumeratorTest
{

	static PasswordValidatorEnumerator passwordValidatorEnumerator;
	@BeforeAll
	public static void setUp()
	{
		IGlobalFactoryProvider provider = new GlobalDependencyMock();
		passwordValidatorEnumerator = new PasswordValidatorEnumerator(provider);
	}

	@Test
	public void getActiveValidatorsTest() {
		passwordValidatorEnumerator.getActiveValidators(new User());
	}
	
}
