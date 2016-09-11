package com.hackathon.trackerproxy.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.DateTime;
import com.hackathon.trackerproxy.db.GPSInfoDao;
import com.hackathon.trackerproxy.representations.BasicAPIResponse;
import com.hackathon.trackerproxy.representations.GPSInfoRequest;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Properties;


/**
 * Created by juyal.shashank on 22/07/16.
 */
@NoArgsConstructor
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class TrackerProxyResource {

    private JerseyClient client;
    private GPSInfoDao gpsInfoDao;


    public TrackerProxyResource(JerseyClient client, GPSInfoDao gpsInfoDao) throws Exception {
        this.gpsInfoDao = gpsInfoDao;
        this.client = client;
    }

    @GET
    @Timed
    @Path("/fetch")
    public Response basicService() throws Exception {


        DateTime now = new DateTime(System.currentTimeMillis());

        BasicAPIResponse basicAPIResponse = new BasicAPIResponse("Success");

        return Response.status(Response.Status.ACCEPTED).entity(basicAPIResponse).build();
    }

    @POST
    @Timed
    @Path("/track")
    public Response track(GPSInfoRequest gpsInfoRequest) throws Exception {
        Properties props = new Properties();
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
            producer.send(new ProducerRecord<String, String>("test", gpsInfoRequest.getGpsDataList().get(0).getDeviceId(),objectMapper.writeValueAsString(gpsInfoRequest)));
        }

        producer.close();
        System.out.println(gpsInfoRequest.toString());
        return Response.status(Response.Status.OK).entity(new BasicAPIResponse("Success")).build();
    }


}
