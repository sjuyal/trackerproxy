package com.otra.osmclient.internal;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.otra.osmclient.OsmClient;
import com.otra.osmclient.response.GetReverseGeoCodingResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HystrixOsmClient implements OsmClient {

    @NonNull
    private final Provider<GetReverseGeoCodingCommand> getProductDetailsV2CommandProvider;


    public GetReverseGeoCodingResponse getReverseGeoCodeFromLatLong(Double lat, Double lon) {
        return getProductDetailsV2CommandProvider.get()
                .withRequest(lat, lon)
                .execute();
    }

}
