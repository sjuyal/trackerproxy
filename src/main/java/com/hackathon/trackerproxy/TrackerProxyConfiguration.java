package com.hackathon.trackerproxy;

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
}
