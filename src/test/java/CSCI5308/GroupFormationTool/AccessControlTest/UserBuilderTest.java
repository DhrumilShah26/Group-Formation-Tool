package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;
import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.GlobalDependencyMock;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserBuilderTest
{
    IGlobalFactoryProvider provider = new GlobalDependencyMock();
    AbstractAclDependencyFactory factory = provider.getAclDependencyFactory();
    @Test
    void build()
    {

        User u = factory.makeUserBuilder()
                .setId(1L)
                .setBannerId("B-000000")
                .setEmail("user@example.com")
                .setFirstName("Dummy")
                .setLastName("User")
                .setPassword("encrypted")
                .build();

        Assertions.assertNotNull(u);
        Assertions.assertEquals(1L, u.getId());
    }
}
