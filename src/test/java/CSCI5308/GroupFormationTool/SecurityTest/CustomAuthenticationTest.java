package CSCI5308.GroupFormationTool.SecurityTest;

import CSCI5308.GroupFormationTool.AccessControlTest.AclDependencyFactoryMock;
import CSCI5308.GroupFormationTool.Security.CustomAuthenticationManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.sql.SQLException;

public class CustomAuthenticationTest
{
    @Test
    public void checkAdminTest()
    {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken("B-000000", "1234");
    }
}
