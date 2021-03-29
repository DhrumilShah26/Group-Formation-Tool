package CSCI5308.GroupFormationTool.SecurityTest;

import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.Security.CustomAuthenticationManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class CustomAuthenticationManagerTest
{
    CustomAuthenticationManager manager = new CustomAuthenticationManager(new GlobalDependencyMock());

    @Test
    public void authenticateTestAdminSuccess()
    {
        Authentication authentication =
                manager.authenticate(new UsernamePasswordAuthenticationToken("B-000000", "Pass@123"));

        Assertions.assertNotNull(authentication);
    }

    @Test
    public void authenticateTestAdminFail()
    {
        Assertions.assertThrows(BadCredentialsException.class,
                () -> manager.authenticate(new UsernamePasswordAuthenticationToken("B-000000", "")));
    }

    @Test
    public void authenticateTestUserNotFound()
    {
        Assertions.assertThrows(AuthenticationServiceException.class, ()->
            manager.authenticate(new UsernamePasswordAuthenticationToken("B-DB_CONNECTION_EX", "Pass@123"))
        );
    }

    @Test void authenticateTestOtherExceptions()
    {
        Assertions.assertThrows(Exception.class, () ->
            manager.authenticate(new UsernamePasswordAuthenticationToken("B-NULL_PTR_EX", ""))
        );
    }

    @Test void authenticateTestBadCredentials()
    {
        Assertions.assertThrows(BadCredentialsException.class, () ->
                manager.authenticate(new UsernamePasswordAuthenticationToken("B-INVALID", ""))
        );
    }

    @Test
    void authenticateTestNormalUser()
    {
        Assertions.assertNotNull(
                manager.authenticate(new UsernamePasswordAuthenticationToken("B-12345", "encrypted"))
        );
    }

    @Test
    void authenticateTestNormalUserBadCredentials()
    {
        Assertions.assertThrows(
                BadCredentialsException.class,
                ()->manager.authenticate(new UsernamePasswordAuthenticationToken("B-12345", ""))
        );
    }
}
