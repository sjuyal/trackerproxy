package com.otra.osmclient.internal;

import com.google.inject.Inject;
import com.otra.osmclient.config.OsmConfiguration;
import com.otra.osmclient.response.GetReverseGeoCodingResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by juyal.shashank on 16/05/17.
 */
@Slf4j
public class GetReverseGeoCodingCommand extends BaseOsmCommand<GetReverseGeoCodingResponse> {

    private final String OSM_PATH = "/nominatim/reverse.php";
    private final OsmConfiguration osmConfiguration;
    private Double lat;
    private Double lon;

    @Inject
    public GetReverseGeoCodingCommand(Client client, OsmConfiguration osmConfiguration) {
        super(client);
        this.osmConfiguration = osmConfiguration;
    }

    public GetReverseGeoCodingCommand withRequest(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
        return this;
    }

    @Override
    protected GetReverseGeoCodingResponse run() throws Exception {
        Response clientResponse = client.target(buildUri())
                .request(MediaType.APPLICATION_JSON).
                        get();
        handleErrors(clientResponse);
        GetReverseGeoCodingResponse getReverseGeoCodingResponse = clientResponse.readEntity(GetReverseGeoCodingResponse.class);
        //System.out.println(responseInString);
        return getReverseGeoCodingResponse;
    }


    private URI buildUri() {
        UriBuilder uriBuilder = UriBuilder
                .fromUri(osmConfiguration.getUrl())
                .path(OSM_PATH);
        uriBuilder.queryParam("lat",lat);
        uriBuilder.queryParam("lon",lon);
        uriBuilder.queryParam("format","json");
        uriBuilder.queryParam("osm_type","W");
        return uriBuilder.build();
    }
}
