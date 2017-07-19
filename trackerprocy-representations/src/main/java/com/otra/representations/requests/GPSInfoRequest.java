package com.otra.representations.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        private double latitude;
        private double longitude;
        private long time;
        private String deviceId;
    }
}
