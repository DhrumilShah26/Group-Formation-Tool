package CSCI5308.GroupFormationTool.Database;

public abstract class AbstractDbDependencyFactory {
    public abstract IDatabaseConfiguration getDatabaseConfiguration();
    public abstract IConnectionManager getConnectionManager();
}
