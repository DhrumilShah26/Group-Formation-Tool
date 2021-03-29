package CSCI5308.GroupFormationTool.PasswordValidationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import CSCI5308.GroupFormationTool.PasswordValidation.MinimumLowercaseValidator;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MinimumLowercaseValidatorTest 
{
	MinimumLowercaseValidator minimumLowercaseValidator= new MinimumLowercaseValidator("5");
	@Test
	public void isValid() 
	{
		String pass = "Passed";
		assertThat(minimumLowercaseValidator.isValid("Passed")).isTrue();
		assertThat(minimumLowercaseValidator.isValid("Fail")).isFalse();
	}


	@Test
	public void getValidatorName()
	{
		String validatorName= minimumLowercaseValidator.getValidatorName();
		assertFalse(Strings.isNullOrEmpty(validatorName));
	}
}
