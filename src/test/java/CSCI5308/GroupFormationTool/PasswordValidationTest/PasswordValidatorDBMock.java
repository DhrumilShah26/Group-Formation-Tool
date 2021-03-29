package CSCI5308.GroupFormationTool.PasswordValidationTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import CSCI5308.GroupFormationTool.PasswordValidation.IPasswordValidatorPersistence;
import CSCI5308.GroupFormationTool.PasswordValidation.PasswordValidator;
import CSCI5308.GroupFormationTool.PasswordValidation.PasswordValidatorType;

public class PasswordValidatorDBMock implements IPasswordValidatorPersistence
{
	@Override
	public HashMap<Long, String> loadActivePasswordValidators() 
	{
		HashMap<Long,String> activeValidators = new HashMap<Long, String>();
		activeValidators.put(1L, PasswordValidatorType.MINLENGTH.toString());
		activeValidators.put(2L, PasswordValidatorType.MAXLENGTH.toString());
		activeValidators.put(3L, PasswordValidatorType.MINLOWERCASE.toString());
		activeValidators.put(4L, PasswordValidatorType.MINUPPERCASE.toString());
		activeValidators.put(5L, PasswordValidatorType.MINSYMBOLS.toString());
		activeValidators.put(6L, PasswordValidatorType.RESTRICTEDCHAR.toString());
		activeValidators.put(7L, PasswordValidatorType.PASSWORDHISTORY.toString());
		return activeValidators;
	}

	@Override
	public String loadConstraintByValidatorId(long id) 
	{
		if(id<=6) 
		{
			return "5";
		}else 
		{
			return ".,#*";
		}
	}

	@Override
	public List<String> fetchPreviousPasswordsByBannerID(String bannerID, int constraint) 
	{
		List<String> passwordList = new ArrayList<String>();
		if(bannerID.equals("B1234567")) 
		{
			passwordList.add("encrypted");
		}else
		{
			passwordList.add("other");
		}
		
		for(int i=0;i<constraint;i++) 
		{
			passwordList.add("password"+i);
		}
		return passwordList;
	}

}
