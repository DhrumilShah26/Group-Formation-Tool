package CSCI5308.GroupFormationTool.Database;

import CSCI5308.GroupFormationTool.IGlobalFactoryProvider;

public class DbDependencyFactory extends AbstractDbDependencyFactory {

    final IGlobalFactoryProvider globalFactoryProvider;

    public DbDependencyFactory(IGlobalFactoryProvider globalFactoryProvider) {
        this.globalFactoryProvider = globalFactoryProvider;
    }


    @Override
    public IDatabaseConfiguration getDatabaseConfiguration() {
        return new DefaultDatabaseConfiguration();
    }

    @Override
    public IConnectionManager getConnectionManager() {
        return new ConnectionManager(getDatabaseConfiguration());
    }
}
