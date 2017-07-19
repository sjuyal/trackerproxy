package com.otra.trackerproxy.commands;

import com.otra.representations.requests.GPSInfoRequest;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.core.Response;

/**
 * Created by juyal.shashank on 15/07/17.
 */
public interface GPSInfoCommand {
    public Response run(GPSInfoRequest gpsInfoRequest, DBI dbi);
}
