package com.otra.osmclient.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.otra.osmclient.OsmClient;
import com.otra.osmclient.internal.HystrixOsmClient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by juyal.shashank on 16/05/17.
 */
public class OsmClientModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(OsmClient.class).to(HystrixOsmClient.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    Client getClient() {
        return ClientBuilder.newClient();
    }
}
