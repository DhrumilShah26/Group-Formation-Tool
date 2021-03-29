package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.UserExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserExistsExceptionTest {
    @Test
    public void getMessage() {
        String bannerId = "B-000000";
        UserExistsException ex = new UserExistsException(bannerId);
        Assertions.assertEquals("User Exists with Banner ID="+bannerId, ex.getMessage());
    }
}
