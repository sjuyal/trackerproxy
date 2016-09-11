package com.hackathon.trackerproxy.representations;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;

/**
 * Created by juyal.shashank on 22/05/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GPSInfoRequest {
    List<GPSdata> gpsDataList;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class GPSdata {
        private double latitute;
        private double longitude;
        private long time;
        private String deviceId;
    }
}
