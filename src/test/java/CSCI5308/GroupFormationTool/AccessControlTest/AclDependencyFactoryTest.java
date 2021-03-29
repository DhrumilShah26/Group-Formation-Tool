package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.AbstractAclDependencyFactory;
import CSCI5308.GroupFormationTool.AccessControl.AclDependencyFactory;
import CSCI5308.GroupFormationTool.GlobalDependencyFactory;
import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AclDependencyFactoryTest
{

    private final AbstractAclDependencyFactory factory;

    public AclDependencyFactoryTest() {
        IGlobalFactoryProvider provider = new GlobalDependencyFactory();
        factory = new AclDependencyFactory(provider);
    }

    @Test
    public void getUserPersistenceTest() {
        Assertions.assertNotNull(factory.getUserPersistence());
    }

    @Test
    public void getCurrentUserTest() {
        Assertions.assertNotNull(factory.getCurrentUser());
    }

}
