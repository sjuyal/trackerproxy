package com.otra.osmclient;

import com.otra.osmclient.request.GetReverseGeoCodingRequest;
import com.otra.osmclient.response.GetReverseGeoCodingResponse;

import java.util.List;

/**
 * Created by juyal.shashank on 16/05/17.
 */
public interface OsmClient {
    GetReverseGeoCodingResponse getReverseGeoCodeFromLatLong(Double lat, Double lon);
}
