package CSCI5308.GroupFormationTool.PasswordValidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinimumLengthValidator extends PasswordValidator
{
	public  MinimumLengthValidator(String constraint) 
	{
		this.constraint = constraint;
	}
	private final Logger logger = LoggerFactory.getLogger(MinimumLengthValidator.class);

	@Override
	public boolean isValid(String password) 
	{
		logger.info("Checking Minimum Length");
		int minLength = Integer.parseInt(this.constraint);
		int passLength = password.length();
		
		if(passLength<minLength) 
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String getValidatorName() 
	{
		return PasswordValidatorType.MINLENGTH.toString();
	}
	
}
