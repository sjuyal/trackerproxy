package com.otra.trackerproxy.commands;

import com.otra.dataaccess.dao.GPSInfoDao;
import com.otra.dataaccess.entities.GPSInfo;
import com.otra.representations.requests.GPSInfoRequest;
import com.otra.representations.responses.BasicAPIResponse;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.core.Response;

/**
 * Created by juyal.shashank on 15/07/17.
 */
public class CreateGPSInfoCommand implements GPSInfoCommand {

    @Override
    public Response run(GPSInfoRequest gpsInfoRequest, DBI dbi) {
        //String query = "";
        Handle handle = dbi.open();
        try {
            GPSInfoDao gpsInfoDao = handle.attach(GPSInfoDao.class);
            if (gpsInfoRequest != null && gpsInfoRequest.getGpsDataList().size() != 0) {
                for (GPSInfoRequest.GPSdata gpsdata : gpsInfoRequest.getGpsDataList()) {
                    GPSInfo gpsInfo = new GPSInfo(gpsdata.getLatitude(), gpsdata.getLongitude(), gpsdata.getTime(), gpsdata.getDeviceId());
                    gpsInfoDao.insert(gpsInfo);
                /*query = "insert into gpsdump (id,device_id,latitude,longitude,time) values (now(),'" +
                        gpsdata.getDeviceId() + "'," +
                        gpsdata.getLatitude() + "," +
                        gpsdata.getLongitude() + "," +
                        gpsdata.getTime() + ");";
                session.execute(query);*/
                }
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new BasicAPIResponse(e.getMessage())).build();
        }

        return Response.status(Response.Status.OK).entity(new BasicAPIResponse("Success")).build();
    }

}
