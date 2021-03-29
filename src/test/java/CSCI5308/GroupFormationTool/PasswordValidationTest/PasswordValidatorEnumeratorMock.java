package CSCI5308.GroupFormationTool.PasswordValidationTest;

import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.PasswordValidation.IPasswordValidatorEnumerator;
import CSCI5308.GroupFormationTool.PasswordValidation.MinimumLengthValidator;
import CSCI5308.GroupFormationTool.PasswordValidation.PasswordValidator;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidatorEnumeratorMock implements IPasswordValidatorEnumerator
{
    @Override
    public List<PasswordValidator> getActiveValidators(User user)
    {
        ArrayList<PasswordValidator> list = new ArrayList<>();
        list.add(new MinimumLengthValidator("5"));
        return list;
    }
}
