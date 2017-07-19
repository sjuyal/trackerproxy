package com.otra.trackerproxy;

import com.otra.osmclient.config.OsmConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TrackerProxyConfiguration extends Configuration {
    @Valid
    @NotNull
    @Getter
    @Setter
    private DataSourceFactory databaseConfiguration = new DataSourceFactory();

    @Valid
    @NotNull
    @Getter
    @Setter
    private JerseyClientConfiguration clientConfiguration = new JerseyClientConfiguration();

    @Valid
    @NotNull
    @Getter
    @Setter
    private OsmConfiguration osmConfiguration = new OsmConfiguration();

}
