package com.otra.osmclient.internal;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

/**
 * Created by juyal.shashank on 16/05/17.
 */
@Slf4j
public abstract class BaseOsmCommand<T> extends HystrixCommand<T> {


    public static final String OSM_GROUP = "osm";
    public static final int DEFAULT_TIME_OUT = 120000;
    protected final Client client;

    protected BaseOsmCommand(Client client) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(OSM_GROUP)).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(DEFAULT_TIME_OUT)));
        this.client = client;
    }

    protected void handleErrors(Response response) {
        if (response.getStatus() >= 400 && response.getStatus() < 500) {
            log.error("Request Failed with {}", response.getStatus());
            throw new HystrixBadRequestException(
                    "Request failed with " + response.getStatus());
        }
        if (response.getStatus() >= 500) {
            log.error("Request Failed with {}", response.getStatus());
            throw new RuntimeException();
        }
    }
}
