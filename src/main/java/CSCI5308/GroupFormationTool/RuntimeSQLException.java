package CSCI5308.GroupFormationTool;

import java.sql.SQLException;

/**
 * @author Aman Vishnani
 *
 * SQLExceptions are checked exceptions and needs exception bubbling.
 * For it to be handled at controller level without affecting Common Closure Principle (CCP)
 * this class has been introduced to give developers flexibility to handle exceptions without affecting method signatures and
 * handle at a later stage.
 */
public class RuntimeSQLException extends RuntimeException
{
    public RuntimeSQLException(SQLException cause)
    {
        super(cause);
    }
}
