package CSCI5308.GroupFormationTool.PasswordValidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinimumSymbolValidator extends PasswordValidator
{
	public MinimumSymbolValidator(String constraint) 
	{
		this.constraint = constraint;
	}
	private final Logger logger = LoggerFactory.getLogger(MinimumSymbolValidator.class);

	@Override
	public boolean isValid(String password) 
	{
		logger.info("Checking Minimum Symbol Validator");
		char[] ch = password.toCharArray();
		int minSymbols = Integer.parseInt(this.constraint);
		int passSymbols = 0;
		
		for (int i=0; i<ch.length; i++) 
		{ 
			if(Character.isLetter(ch[i]) == false 
					&& Character.isDigit(ch[i]) == false) 
			{
				passSymbols++;
			}
		} 
		
		if(passSymbols<minSymbols) 
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public String getValidatorName() 
	{
		return PasswordValidatorType.MINSYMBOLS.toString();
	}
	
}
