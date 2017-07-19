package com.otra.nucleo.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.otra.dataaccess.dao.*;
import com.otra.dataaccess.entities.*;
import com.otra.dataaccess.enums.TableType;
import com.otra.nucleo.utils.DBUtils;
import com.otra.nucleo.utils.MapUtils;
import com.otra.nucleo.utils.MetricUtils;
import com.otra.osmclient.OsmClient;
import com.otra.osmclient.config.OsmClientModule;
import com.otra.osmclient.response.GetReverseGeoCodingResponse;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by juyal.shashank on 26/11/16.
 */
@Slf4j
public class RoadRunner {


    public static void main(String args[]) throws Exception {
        DBI dbi = DBUtils.CassandraDBIProvider();
        GPSInfoDao gpsInfoDao = dbi.onDemand(GPSInfoDao.class);


        DBI dbi2 = DBUtils.MysqlDBIProvider();
        Handle handle = dbi2.open();

        OsmClient osmClient = getOsmClient();

        DeviceDAO deviceDAO = handle.attach(DeviceDAO.class);
        CheckpointDAO checkpointDAO = handle.attach(CheckpointDAO.class);
        DriverDAO driverDAO = handle.attach(DriverDAO.class);
        CampaignDAO campaignDAO = handle.attach(CampaignDAO.class);
        MetricsDAO metricsDAO = handle.attach(MetricsDAO.class);
        LocationDAO locationDAO = handle.attach(LocationDAO.class);

        List<Device> devices = deviceDAO.getAllActiveDevices();
        for (Device device : devices) {
            Driver driver = driverDAO.get(device.getDriverId());
            if (driver == null) {
                System.out.println("No Such Driver Found in DB");
                log.info("No Such Driver Found in DB");
                continue;
            }
            if (!driver.getIsApproved()){
                System.out.println("Driver " + driver.getId() + " is not Approved");
                log.info("Driver " + driver.getId() + " is not Approved");
                continue;
            }
            Campaign campaign = campaignDAO.get(driver.getCampaignId());
            if (campaign == null) {
                System.out.println("No Such Campaign Found in DB");
                log.info("No Such Campaign Found in DB");
                continue;
            }
            if (!campaign.isRunning()){
                System.out.println("Campaign is not running");
                log.info("Campaign is not running");
                continue;
            }
            Checkpoint checkpoint = checkpointDAO.get(device.getId());
            Long startTime = null;
            if(checkpoint == null){
                checkpointDAO.create(device.getId(),0L);
                startTime = 0L;
            }else{
                startTime = checkpoint.getTime();
            }
            List<GPSInfo> gpsInfos = gpsInfoDao.getGPSInfosForDevice(device.getGpsDevNum(), startTime);
            if(gpsInfos == null || gpsInfos.size()==0) continue;

            LatLng prevLatLng = null;
            Long prevTime = null;
            GetReverseGeoCodingResponse prevGetReverseGeoCodingResponse = null;

            Boolean flag = true;
            for (GPSInfo gpsInfo : gpsInfos) {
                GetReverseGeoCodingResponse geoCodingResponse = osmClient.getReverseGeoCodeFromLatLong(gpsInfo.getLatitude(), gpsInfo.getLongitude());
                System.out.println(geoCodingResponse);
                if(flag){
                    prevGetReverseGeoCodingResponse = geoCodingResponse;
                    prevLatLng = new LatLng(gpsInfo.getLatitude(),gpsInfo.getLongitude());
                    prevTime = gpsInfo.getTime();
                    flag = false;
                    if(geoCodingResponse != null){
                        if(locationDAO.get(geoCodingResponse.getOsmId()) == null){
                            Location location = getLocationInfo(geoCodingResponse);
                            locationDAO.insert(location);
                        }
                    }
                }else if(gpsInfo.getTime()!=prevTime){
                    Set<String> areas = new HashSet<>();
                    areas.add(prevGetReverseGeoCodingResponse.getOsmId());
                    areas.add(geoCodingResponse.getOsmId());
                    Double distance = (MapUtils.distance(prevLatLng.getLatitude(), gpsInfo.getLatitude(),
                            prevLatLng.getLongitude(), gpsInfo.getLongitude(), 0.0, 0.0)) / areas.size();
                    Integer campaignId = campaign.getId();
                    Integer driverId = driver.getId();

                    List<MetricPair> monthlyMetricPairs = MetricUtils.findMonthlyMetricPairs(distance, new DateTime(prevTime), new DateTime(gpsInfo.getTime()));
                    List<MetricPair> dailyMetricPairs = MetricUtils.findDailyMetricPairs(distance, new DateTime(prevTime), new DateTime(gpsInfo.getTime()));
                    List<MetricPair> hourlyMetricPairs = MetricUtils.findHourlyMetricPairs(distance,new DateTime(prevTime),new DateTime(gpsInfo.getTime()));

                    for(MetricPair metricPair: monthlyMetricPairs){
                        Iterator<String> iterator = areas.iterator();
                        while(iterator.hasNext()){
                            metricsDAO.insert(Integer.parseInt(metricPair.getMetric()),iterator.next(),
                                    campaignId, driverId, metricPair.getDistance()/areas.size(),
                                    metricPair.getTime()/areas.size(), null, null, TableType.METRIC_MONTHLY.toString());
                        }
                    }

                    for(MetricPair metricPair: dailyMetricPairs){
                        Iterator<String> iterator = areas.iterator();
                        while(iterator.hasNext()){
                            metricsDAO.insert(Integer.parseInt(metricPair.getMetric()),iterator.next(),
                                    campaignId, driverId, metricPair.getDistance()/areas.size(),
                                    metricPair.getTime()/areas.size(), null, null, TableType.METRIC_DAILY.toString());
                        }
                    }

                    for(MetricPair metricPair: hourlyMetricPairs){
                        Iterator<String> iterator = areas.iterator();
                        while(iterator.hasNext()){
                            metricsDAO.insert(Integer.parseInt(metricPair.getMetric()),iterator.next(),
                                    campaignId, driverId, metricPair.getDistance()/areas.size(),
                                    metricPair.getTime()/areas.size(), null, null, TableType.METRIC_HOURLY.toString());
                        }
                    }

                    prevGetReverseGeoCodingResponse = geoCodingResponse;
                    prevLatLng = new LatLng(gpsInfo.getLatitude(),gpsInfo.getLongitude());
                    prevTime = gpsInfo.getTime();
                    if(geoCodingResponse != null){
                        if(locationDAO.get(geoCodingResponse.getOsmId()) == null){
                            Location location = getLocationInfo(geoCodingResponse);
                            locationDAO.insert(location);
                        }
                    }
                }
            }
            checkpointDAO.update(device.getId(),gpsInfos.get(gpsInfos.size()-1).getTime());
        }
    }

    public static OsmClient getOsmClient() {
        Injector injector = Guice.createInjector(new OsmClientModule());
        OsmClient osmClient = injector.getInstance(OsmClient.class);
        return osmClient;
    }

    public static Location getLocationInfo(GetReverseGeoCodingResponse geoCodingResponse){
        Location location = new Location();
        location.setRoadId(geoCodingResponse.getOsmId());
        if(geoCodingResponse.getAddress() != null){
            GetReverseGeoCodingResponse.Address address = geoCodingResponse.getAddress();
            location.setRoadName(address.getRoad());
            location.setNeighbourhood(address.getNeighbourhood());
            location.setSuburb(address.getSuburb());
            location.setCity(address.getCity());
            location.setCounty(address.getCounty());
            location.setStateDistrict(address.getStateDistrict());
            location.setPostcode(address.getPostcode());
            location.setCountry(address.getCountry());
            location.setCountryCode(address.getCountryCode());
        }
        return location;
    }

}
