package CSCI5308.GroupFormationTool.SecurityTest;

import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.Security.SignupController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class SignUpControllerTest
{
    SignupController controller = new SignupController(new GlobalDependencyMock());


    @Test
    public void displaySignUp()
    {
        Assertions.assertNotNull(controller.displaySignUp(null));
    }

    @Test
    public void processSignUpTest() {
        ModelAndView mav = controller.processSignUp(
                "B-1234", "123456", "123456",
                "Aman", "V", "am@an.vis"
        );
        Assertions.assertNotNull(mav);
    }

    @Test
    public void processSignUpTestUserExists() {
        ModelAndView mav = controller.processSignUp(
                "B-000000", "123456", "123456",
                "Aman", "V", "am@an.vis"
        );
        Assertions.assertNotNull(mav);
    }

    @Test
    public void processSignUpTestDbIssue() {
        ModelAndView mav = controller.processSignUp(
                "B-DB_CONNECTION_EX", "123456", "123456",
                "Aman", "V", "am@an.vis"
        );
        Assertions.assertNotNull(mav);
    }

    @Test
    public void processSignUpTestPasswordInValid() {
        ModelAndView mav = controller.processSignUp(
                "B-1234", "1234", "1234",
                "Aman", "V", "am@an.vis"
        );
        Assertions.assertNotNull(mav);
    }
}
