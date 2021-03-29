package CSCI5308.GroupFormationTool.PasswordValidation;

import java.util.List;

import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordHistoryValidator extends PasswordValidator
{
	final IPasswordValidatorPersistence validatorDB;
	final IPasswordEncryption passwordEncryption;
	User user;
	private final Logger logger = LoggerFactory.getLogger(PasswordHistoryValidator.class);

	public PasswordHistoryValidator(String constraint, User user, IPasswordValidatorPersistence validatorDB,
									IPasswordEncryption passwordEncryption)
	{
		this.constraint = constraint;
		this.user = user;
		this.validatorDB = validatorDB;
		this.passwordEncryption = passwordEncryption;
	}
	
	@Override
	public boolean isValid(String password) 
	{
		List<String> previousPass = validatorDB.fetchPreviousPasswordsByBannerID(user.getBannerID(), Integer.parseInt(constraint));
		for(int i=0; i<previousPass.size(); i++)
		{
			if(passwordEncryption.matches(password, previousPass.get(i))) 
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public String getValidatorName() 
	{
		return PasswordValidatorType.PASSWORDHISTORY.toString();
	}
}
