package CSCI5308.GroupFormationTool.PasswordValidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinimumLowercaseValidator extends PasswordValidator
{
	public MinimumLowercaseValidator(String constraint) 
	{
		this.constraint = constraint;
	}
	private final Logger logger = LoggerFactory.getLogger(MinimumLowercaseValidator.class);

	@Override
	public boolean isValid(String password) 
	{
		logger.info("Checking Minimum Lowercase Validator");
		int minLower = Integer.parseInt(this.constraint);
		int passLower = 0;
		
		for (int i=0; i<password.length(); i++)
		{
			if (Character.isLowerCase(password.charAt(i)))
			{
				passLower++;
			}
		}
		
		if(passLower<minLower) 
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public String getValidatorName() 
	{
		return PasswordValidatorType.MINLOWERCASE.toString();
	}
	
}
