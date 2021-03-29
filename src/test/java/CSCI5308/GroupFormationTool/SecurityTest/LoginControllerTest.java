package CSCI5308.GroupFormationTool.SecurityTest;

import CSCI5308.GroupFormationTool.Security.LoginController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginControllerTest
{
    LoginController controller = new LoginController();

    @Test
    public void loginTest() {
        Assertions.assertNotNull(controller.login(null));
    }

    @Test
    public void loginErrorTest() {
        Assertions.assertNotNull(controller.loginError(null));
    }
}
