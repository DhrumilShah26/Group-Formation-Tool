package CSCI5308.GroupFormationTool.PasswordValidationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import CSCI5308.GroupFormationTool.PasswordValidation.MaximumLengthValidator;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MaximumLengthValidatorTest
{
	MaximumLengthValidator maximumLengthValidator = new MaximumLengthValidator("5");
	@Test
	public void isValid() 
	{
		assertThat(maximumLengthValidator.isValid("pass")).isTrue();
		assertThat(maximumLengthValidator.isValid("failed")).isFalse();
	}


	@Test
	public void getValidatorName(){
		String validatorName= maximumLengthValidator.getValidatorName();
		assertFalse(Strings.isNullOrEmpty(validatorName));
	}


}
