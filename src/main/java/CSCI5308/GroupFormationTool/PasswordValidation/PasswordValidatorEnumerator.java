package CSCI5308.GroupFormationTool.PasswordValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordValidatorEnumerator implements IPasswordValidatorEnumerator
{
	private final Logger logger = LoggerFactory.getLogger(PasswordValidator.class);
	private final IPasswordValidatorPersistence validatorDB;
	private HashMap<Long, String> validators;
	private final IPasswordEncryption passwordEncryption;
	
	public PasswordValidatorEnumerator(IGlobalFactoryProvider factoryProvider)
	{
		this.validatorDB = factoryProvider.getPasswordValidatorDependencyFactory().getPasswordValidatorPersistence();
		this.passwordEncryption = factoryProvider.getSecurityFactory().getPasswordEncryption();
	}

	public List<PasswordValidator> getActiveValidators(User user)
	{
		validators = validatorDB.loadActivePasswordValidators();

		logger.debug("Validators active: "+validators.values());
		List<PasswordValidator> activeValidators = new ArrayList<>();

		for (Map.Entry<Long, String> item : validators.entrySet())
		{
			long key = (long) item.getKey();
			String value = (String) item.getValue();
			String constraint = validatorDB.loadConstraintByValidatorId(key);
	        
			if(value.equals(PasswordValidatorType.MINLENGTH.toString())) 
	        {
	        	activeValidators.add(new MinimumLengthValidator(constraint));
	        }else if(value.equals(PasswordValidatorType.MAXLENGTH.toString())) 
	        {
	        	activeValidators.add(new MaximumLengthValidator(constraint));
	        }else if(value.equals(PasswordValidatorType.MINUPPERCASE.toString())) 
	        {
	        	activeValidators.add(new MinimumUppercaseValidator(constraint));
	        }else if(value.equals(PasswordValidatorType.MINLOWERCASE.toString())) 
	        {
	        	activeValidators.add(new MinimumLowercaseValidator(constraint));
	        }else if(value.equals(PasswordValidatorType.MINSYMBOLS.toString())) 
	        {
	        	activeValidators.add(new MinimumSymbolValidator(constraint));
	        }else if(value.equals(PasswordValidatorType.RESTRICTEDCHAR.toString())) 
	        {
	        	activeValidators.add(new RestrictedCharacterValidator(constraint));
	        }else if(value.equals(PasswordValidatorType.PASSWORDHISTORY.toString())) 
	        {
	        	activeValidators.add(new PasswordHistoryValidator(constraint, user, validatorDB, passwordEncryption));
	        }   
		}
		
		return activeValidators;
	}
	
}
