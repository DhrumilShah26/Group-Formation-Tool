package CSCI5308.GroupFormationTool.SecurityTest;

import CSCI5308.GroupFormationTool.Security.AuthFailureHandler;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;

import javax.servlet.ServletException;
import java.io.IOException;

public class AuthFailureHandlerTest {
    AuthFailureHandler handler = new AuthFailureHandler();

    @Test
    public void onAuthenticationFailureTest() throws IOException, ServletException {
        handler.onAuthenticationFailure(
                new MockHttpServletRequest(), new MockHttpServletResponse(),
                new BadCredentialsException("Test")
        );
    }
}
