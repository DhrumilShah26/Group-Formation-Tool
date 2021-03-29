package CSCI5308.GroupFormationTool.PasswordValidationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import CSCI5308.GroupFormationTool.PasswordValidation.MinimumLengthValidator;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MinimumLengthValidatorTest 
{
	MinimumLengthValidator minimumLengthValidator=new MinimumLengthValidator("5");
	@Test
	public void isValid() 
	{
		assertThat(minimumLengthValidator.isValid("passed")).isTrue();
		assertThat(minimumLengthValidator.isValid("fail")).isFalse();
	}

	@Test
	public void getValidatorName()
	{
		String validatorName= minimumLengthValidator.getValidatorName();
		assertFalse(Strings.isNullOrEmpty(validatorName));
	}

}
