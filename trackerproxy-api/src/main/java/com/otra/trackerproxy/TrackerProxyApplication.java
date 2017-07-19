package com.otra.trackerproxy;

import com.hubspot.dropwizard.guice.GuiceBundle;
import com.otra.osmclient.config.OsmClientModule;
import com.otra.trackerproxy.init.InitProvider;
import com.otra.trackerproxy.resources.TrackerProxyResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TrackerProxyApplication extends Application<TrackerProxyConfiguration> {

    private Bootstrap<TrackerProxyConfiguration> bootstrap;
    public static void main(final String[] args) throws Exception {
        new TrackerProxyApplication().run(args);
    }

    @Override
    public String getName() {
        return "TrackerProxy";
    }

    @Override
    public void initialize(final Bootstrap<TrackerProxyConfiguration> bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Override
    public void run(final TrackerProxyConfiguration configuration,
                    final Environment environment) throws Exception {
        GuiceBundle<TrackerProxyConfiguration> guiceBundle = GuiceBundle.<TrackerProxyConfiguration>newBuilder()
                .setConfigClass(TrackerProxyConfiguration.class)
                .addModule(new InitProvider(configuration, environment))
                .addModule(new OsmClientModule())
                .build();
        bootstrap.addBundle(guiceBundle);
        TrackerProxyResource trackerProxyResource = guiceBundle.getInjector().getInstance(TrackerProxyResource.class);
        environment.jersey().register(trackerProxyResource);
    }

}
