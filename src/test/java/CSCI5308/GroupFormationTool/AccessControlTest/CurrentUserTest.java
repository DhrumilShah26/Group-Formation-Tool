package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;
import CSCI5308.GroupFormationTool.AccessControl.CurrentUser;
import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

public class CurrentUserTest {

    private final AbstractAclDependencyFactory factory;
    private final CurrentUser user;
    public CurrentUserTest()
    {
        IGlobalFactoryProvider provider = new GlobalDependencyMock();
        factory = provider.getAclDependencyFactory();
        user = new CurrentUser(factory);
    }

    @Test
    public void getCurrentAuthenticatedUserTest()
    {
        Assertions.assertNull(user.getCurrentAuthenticatedUser());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("B-000000", 1234, new ArrayList<>())
        );
        Assertions.assertNotNull(user.getCurrentAuthenticatedUser());

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("B-000000", 1234)
        );
        Assertions.assertNull(user.getCurrentAuthenticatedUser());
    }

    @AfterAll
    static void cleanUp() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
