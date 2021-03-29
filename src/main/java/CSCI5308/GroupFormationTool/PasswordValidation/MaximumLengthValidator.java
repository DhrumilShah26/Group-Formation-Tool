package CSCI5308.GroupFormationTool.PasswordValidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaximumLengthValidator extends PasswordValidator
{
	private final Logger logger = LoggerFactory.getLogger(MaximumLengthValidator.class);
	public MaximumLengthValidator(String constraint) 
	{
		this.constraint = constraint;
	}


	@Override
	public boolean isValid(String password) 
	{
		logger.info("Checking Maximum Length");
		int maxLength = Integer.parseInt(this.constraint);
		int passLength = password.length();
		if(passLength>maxLength) 
		{
			return false;
		}
		return true;
	}

	@Override
	public String getValidatorName() 
	{
		return PasswordValidatorType.MAXLENGTH.toString();
	}
	 
}
