package CSCI5308.GroupFormationTool.AccessControl;

public class UserExistsException extends RuntimeException {
    private String bannerId = null;
    public UserExistsException(String bannerId) {
        this.bannerId = bannerId;
    }

    @Override
    public String getMessage() {
        return "User Exists with Banner ID="+bannerId;
    }
}
