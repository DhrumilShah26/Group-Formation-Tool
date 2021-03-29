package CSCI5308.GroupFormationTool.SecurityTest;

import CSCI5308.GroupFormationTool.Security.BCryptPasswordEncryption;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BCryptPasswordEncryptionTest {
    @Test
    public void encodeTest() {
        IPasswordEncryption passwordEncryption = new BCryptPasswordEncryption();
        Assertions.assertNotNull(passwordEncryption.encryptPassword("a"));
    }

    @Test
    public void matchesTest() {
        IPasswordEncryption passwordEncryption = new BCryptPasswordEncryption();
        String encryptedPassword = passwordEncryption.encryptPassword("a");
        Assertions.assertNotNull(encryptedPassword);

        Assertions.assertTrue(passwordEncryption.matches("a", encryptedPassword));
    }

}
