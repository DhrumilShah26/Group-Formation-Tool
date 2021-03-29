package CSCI5308.GroupFormationTool.PasswordValidationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import CSCI5308.GroupFormationTool.PasswordValidation.RestrictedCharacterValidator;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestrictedCharacterValidatorTest 
{
	RestrictedCharacterValidator restrictedCharacterValidator= new RestrictedCharacterValidator("#");
	@Test
	public void isValid() 
	{
		assertThat(restrictedCharacterValidator.isValid("Pas@sed")).isTrue();
		assertThat(restrictedCharacterValidator.isValid("Fail#ed")).isFalse();
	}

	@Test
	public void getValidatorName()
	{
		String validatorName= restrictedCharacterValidator.getValidatorName();
		assertFalse(Strings.isNullOrEmpty(validatorName));
	}

}
