package com.otra.trackerproxy.init;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.otra.osmclient.config.OsmConfiguration;
import com.otra.trackerproxy.TrackerProxyConfiguration;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import lombok.AllArgsConstructor;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by juyal.shashank on 22/04/17.
 */
@AllArgsConstructor
public class InitProvider extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(InitProvider.class);
    private TrackerProxyConfiguration trackerProxyConfiguration;
    private Environment environment;


    @Override
    protected void configure() {
        bind(OsmConfiguration.class).toInstance(trackerProxyConfiguration.getOsmConfiguration());
    }

    @Singleton
    @Provides
    DBI getDBI() {
        //DBIFactory factory = new DBIFactory();
        //DBI dbi = factory.build(environment, trackerProxyConfiguration.getDatabaseConfiguration(), "mysql");
        //DBI dbi = new DBI("jdbc:cassandra:Database=cadredb;Port=9042;Server=localhost;");
        DBI dbi = new DBI("jdbc:cassandra://localhost:9160/cadredb");
        return dbi;
    }

    /*@Singleton
    @Provides
    Client getClient() {
        return ClientBuilder.newClient();
    }*/

}
