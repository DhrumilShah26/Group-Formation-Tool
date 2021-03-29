package CSCI5308.GroupFormationTool.PasswordValidationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import CSCI5308.GroupFormationTool.PasswordValidation.MinimumUppercaseValidator;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MinimumUppercaseValidatorTest 
{
	MinimumUppercaseValidator minimumUppercaseValidator=new MinimumUppercaseValidator("1");
	@Test
	public void isValid() 
	{
		assertThat(minimumUppercaseValidator.isValid("Passed")).isTrue();
		assertThat(minimumUppercaseValidator.isValid("fail")).isFalse();
		
	}

	@Test
	public void getValidatorName()
	{
		String validatorName= minimumUppercaseValidator.getValidatorName();
		assertFalse(Strings.isNullOrEmpty(validatorName));
	}

}
