package com.otra.nucleo.core;

import com.otra.dataaccess.dao.BoundariesDAO;
import com.otra.nucleo.utils.DBUtils;
import com.otra.representations.responses.CrawlerResponse;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by juyal.shashank on 19/11/17.
 */
public class Crawler {

    public static final String PATH = "/api/v2/polygon/client/show/bulk";

    public static Client getClient() {
        return ClientBuilder.newClient();
    }

    public static void main(String args[]) throws Exception {

        Set<String> coveredIds = new HashSet<>();

        Queue<String> queue = new LinkedList<>();
        queue.add("d016aca9b7322bc7943f");

        Client client = getClient();
        DBI mysqldbi = DBUtils.MysqlDBIProvider();
        Handle handle = mysqldbi.open();
        BoundariesDAO boundariesDAO = handle.attach(BoundariesDAO.class);

        //int count =10;

        while(!queue.isEmpty()){
            //count--;
            //if(count == 0) break;
            String idToConsider = queue.peek();
            queue.remove();
            if(coveredIds.contains(idToConsider)) continue;
            //TimeUnit.SECONDS.sleep(1);
            Response clientResponse = client.target(buildUri(idToConsider))
                    .request(MediaType.APPLICATION_JSON).
                            get();
            coveredIds.add(idToConsider);
            List<CrawlerResponse> crawlerResponseList = clientResponse.readEntity(new GenericType<List<CrawlerResponse>>() {});

            CrawlerResponse crawlerResponse = crawlerResponseList.get(0);

            List<List<Double>> polygonCoordinates = crawlerResponse.getPolygon().coordinates.get(0);
            StringBuilder sb = new StringBuilder();
            for(List<Double> coordinates: polygonCoordinates){
                Double lat = coordinates.get(1);
                Double lon = coordinates.get(0);
                sb.append(lat+":"+lon +",");
            }
            sb.deleteCharAt(sb.length()-1);
            System.out.println(crawlerResponse.getName());
            boundariesDAO.create(crawlerResponse.getName(), sb.toString());
            List<CrawlerResponse.Nearby> nearbies = crawlerResponse.getNearby();
            for(CrawlerResponse.Nearby nearby: nearbies){
                queue.add(nearby.getUuid());
            }
        }

    }

    public static URI buildUri(String areaId) {
        UriBuilder uriBuilder = UriBuilder
                .fromUri("https://regions.housing.com")
                .path(PATH);
        uriBuilder.queryParam("source","web");
        uriBuilder.queryParam("uuids",areaId);
        return uriBuilder.build();
    }
}
