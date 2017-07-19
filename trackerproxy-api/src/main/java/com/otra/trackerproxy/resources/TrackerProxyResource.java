package com.otra.trackerproxy.resources;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.inject.Inject;
import com.otra.osmclient.OsmClient;
import com.otra.osmclient.response.GetReverseGeoCodingResponse;
import com.otra.representations.requests.GPSInfoRequest;
import com.otra.representations.responses.BasicAPIResponse;
import com.otra.trackerproxy.commands.CreateGPSInfoCommand;
import com.otra.trackerproxy.commands.GPSInfoCommand;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created by juyal.shashank on 22/06/17.
 */
@Path("/v1")
@Data
@Produces(MediaType.APPLICATION_JSON)
public class TrackerProxyResource {

    private DBI dbi;
    private OsmClient osmClient;

    @Inject
    public TrackerProxyResource(DBI dbi, OsmClient osmClient){
        this.dbi = dbi;
        this.osmClient = osmClient;
    }

    @GET
    @Timed
    @Path("/fetch")
    public Response basicService(@QueryParam("lat") Double lat,
                                 @QueryParam("lon") Double lon) throws Exception {
        GetReverseGeoCodingResponse getReverseGeoCodingResponse = osmClient.getReverseGeoCodeFromLatLong(lat, lon);
        DateTime now = new DateTime(System.currentTimeMillis());
        return Response.status(Response.Status.ACCEPTED).entity(getReverseGeoCodingResponse).build();
    }

    @POST
    @Timed
    @Path("/track")
    public Response track(GPSInfoRequest gpsInfoRequest) throws Exception {
        GPSInfoCommand createGPSInfoCommand = new CreateGPSInfoCommand();
        return createGPSInfoCommand.run(gpsInfoRequest, dbi);

        /*try {
            Cluster cluster = Cluster.builder().withPort(9042).addContactPoint("localhost").build();
            Session session = cluster.connect("cadredb");
            String query = "";
            if (gpsInfoRequest != null && gpsInfoRequest.getGpsDataList().size() != 0) {
                for (GPSInfoRequest.GPSdata gpsdata : gpsInfoRequest.getGpsDataList()) {
                    query = "insert into gpsdump (id,device_id,latitude,longitude,time) values (now(),'" +
                            gpsdata.getDeviceId() + "'," +
                            gpsdata.getLatitude() + "," +
                            gpsdata.getLongitude() + "," +
                            gpsdata.getTime() + ");";
                    session.execute(query);
                }
            }

            /*Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            Producer<String, String> producer = new KafkaProducer<String, String>(props);
            ObjectMapper objectMapper = new ObjectMapper();
            if(gpsInfoRequest != null && gpsInfoRequest.getGpsDataList().size()!=0){
                for(GPSInfoRequest.GPSdata gpSdata : gpsInfoRequest.getGpsDataList()){
                    producer.send(new ProducerRecord<String, String>("cadre",
                            gpsInfoRequest.getGpsDataList().get(0).getDeviceId(),objectMapper.writeValueAsString(gpSdata)));
                }
            }

            producer.close();
            System.out.println(gpsInfoRequest.toString());

            return Response.status(Response.Status.OK).entity(new BasicAPIResponse("Success")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new BasicAPIResponse(e.getMessage())).build();
        }*/
    }
}
