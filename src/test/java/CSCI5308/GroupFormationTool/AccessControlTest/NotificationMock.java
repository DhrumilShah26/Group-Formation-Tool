package CSCI5308.GroupFormationTool.AccessControlTest;

import CSCI5308.GroupFormationTool.AccessControl.IUserNotifications;
import CSCI5308.GroupFormationTool.AccessControl.User;

public class NotificationMock implements IUserNotifications
{
    @Override
    public void sendUserLoginCredentials(User user, String rawPassword) {

    }
}
