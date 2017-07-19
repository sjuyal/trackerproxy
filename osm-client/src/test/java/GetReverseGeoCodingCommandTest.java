import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.*;
import com.otra.osmclient.OsmClient;
import com.otra.osmclient.internal.HystrixOsmClient;
import com.otra.osmclient.response.GetReverseGeoCodingResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by juyal.shashank on 16/07/17.
 */
public class GetReverseGeoCodingCommandTest {
    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(OsmClient.class).to(HystrixOsmClient.class).in(Singleton.class);
                //bind(SfmConfiguration.class).toInstance(retailProcConfiguration.getSfmConfiguration());
            }
            @Provides
            @Singleton
            Client getClient() {
                return ClientBuilder.newClient();
            }
        });
        OsmClient app = injector.getInstance(OsmClient.class);


        Double lat = 12.912751;
        Double lon = 77.644815;
        GetReverseGeoCodingResponse response = app.getReverseGeoCodeFromLatLong(lat, lon);
        System.out.println(response);
    }
}
