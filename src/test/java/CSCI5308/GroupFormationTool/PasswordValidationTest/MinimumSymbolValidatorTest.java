package CSCI5308.GroupFormationTool.PasswordValidationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import CSCI5308.GroupFormationTool.PasswordValidation.MinimumSymbolValidator;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MinimumSymbolValidatorTest 
{
	MinimumSymbolValidator minimumSymbolValidator= new MinimumSymbolValidator("1");
	@Test
	public void isValid() 
	{
		assertThat(minimumSymbolValidator.isValid("Pas@sed")).isTrue();
		assertThat(minimumSymbolValidator.isValid("pass")).isFalse();
	}

	@Test
	public void getValidatorName()
	{
		String validatorName= minimumSymbolValidator.getValidatorName();
		assertFalse(Strings.isNullOrEmpty(validatorName));
	}

}
