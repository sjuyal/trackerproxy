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

import java.math.BigDecimal;
import java.util.*;

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

        //OsmClient osmClient = getOsmClient();

        DeviceDAO deviceDAO = handle.attach(DeviceDAO.class);
        CheckpointDAO checkpointDAO = handle.attach(CheckpointDAO.class);
        DriverDAO driverDAO = handle.attach(DriverDAO.class);
        CampaignDAO campaignDAO = handle.attach(CampaignDAO.class);
        MetricsDAO metricsDAO = handle.attach(MetricsDAO.class);
        LocationDAO locationDAO = handle.attach(LocationDAO.class);
        BoundariesDAO boundariesDAO = handle.attach(BoundariesDAO.class);

        // Preloading boundaries in memory
        List<Boundaries> boundariesList = boundariesDAO.getAllBoundaries();
        for (Boundaries boundary : boundariesList) {
            List<Coordinate> areas = new ArrayList<>();
            String[] latlongs = boundary.getBoundaries().split(",");
            for (String latlong : latlongs) {
                String[] coordinate = latlong.split(":");
                areas.add(new Coordinate(Double.parseDouble(coordinate[1]), Double.parseDouble(coordinate[0])));
            }
            boundary.setCoordinates(areas);
        }

        List<Device> devices = deviceDAO.getAllActiveDevices();
        for (Device device : devices) {
            Driver driver = driverDAO.get(device.getDriverId());
            if (driver == null) {
                System.out.println("No Such Driver Found in DB");
                log.info("No Such Driver Found in DB");
                continue;
            }
            if (!driver.getIsApproved()) {
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
            if (!campaign.isRunning()) {
                System.out.println("Campaign is not running");
                log.info("Campaign is not running");
                continue;
            }
            Checkpoint checkpoint = checkpointDAO.get(device.getId());
            Long startTime = null;
            if (checkpoint == null) {
                checkpointDAO.create(device.getId(), 0L);
                startTime = 0L;
            } else {
                startTime = checkpoint.getTime();
            }
            List<GPSInfo> gpsInfos = gpsInfoDao.getGPSInfosForDevice(device.getGpsDevNum(), startTime);
            if (gpsInfos == null || gpsInfos.size() == 0) continue;

            LatLng prevLatLng = null;
            Long prevTime = null;
            Boundaries prevBoundary = null;

            Boolean flag = true;
            for (GPSInfo gpsInfo : gpsInfos) {
                Coordinate coordinate = new Coordinate(gpsInfo.getLatitude(), gpsInfo.getLongitude());
                Boundaries boundary = findBiggestMatchingBoundary(boundariesList, coordinate);

                System.out.println(boundary.getName());

                Integer locationId = null;
                if (boundary != null) {
                    Location location = locationDAO.get(boundary.getId());
                    if (location == null) {
                        Location newLocation = new Location();
                        newLocation.setAreaId(boundary.getId());
                        locationId = locationDAO.insert(newLocation);
                    }else
                        locationId = location.getId();
                }
                boundary.setLocationId(locationId);
                if (flag) {
                    prevBoundary = boundary;
                    prevLatLng = new LatLng(gpsInfo.getLatitude(), gpsInfo.getLongitude());
                    prevTime = gpsInfo.getTime();
                    flag = false;
                } else if (gpsInfo.getTime() != prevTime) {
                    Set<Integer> areas = new HashSet<>();
                    areas.add(prevBoundary.getLocationId());
                    areas.add(boundary.getLocationId());
                    Double distance = (MapUtils.distance(prevLatLng.getLatitude(), gpsInfo.getLatitude(),
                            prevLatLng.getLongitude(), gpsInfo.getLongitude(), 0.0, 0.0)) / areas.size();
                    Integer campaignId = campaign.getId();
                    Integer driverId = driver.getId();

                    List<MetricPair> monthlyMetricPairs = MetricUtils.findMonthlyMetricPairs(distance, new DateTime(prevTime), new DateTime(gpsInfo.getTime()));
                    List<MetricPair> dailyMetricPairs = MetricUtils.findDailyMetricPairs(distance, new DateTime(prevTime), new DateTime(gpsInfo.getTime()));
                    List<MetricPair> hourlyMetricPairs = MetricUtils.findHourlyMetricPairs(distance, new DateTime(prevTime), new DateTime(gpsInfo.getTime()));

                    for (MetricPair metricPair : monthlyMetricPairs) {
                        Iterator<Integer> iterator = areas.iterator();
                        while (iterator.hasNext()) {
                            Integer currentLocation = iterator.next();
                            Metrics metrics = metricsDAO.getMetricsForMetricLocationCampaignDriverId(Integer.parseInt(metricPair.getMetric()),
                                    currentLocation, campaignId, driverId, TableType.METRIC_MONTHLY.toString());
                            if(metrics == null){
                                metricsDAO.insert(Integer.parseInt(metricPair.getMetric()), currentLocation,
                                    campaignId, driverId, metricPair.getDistance(),
                                    metricPair.getTime() / areas.size(), null, null, null, TableType.METRIC_MONTHLY.toString());
                            }
                            else{
                                metrics.setDistance(metrics.getDistance().add(new BigDecimal(metricPair.getDistance())));
                                metrics.setTime(metrics.getTime().add(new BigDecimal(metricPair.getTime() / areas.size())));
                                metricsDAO.update(metrics, TableType.METRIC_MONTHLY.toString());
                            }
                        }
                    }

                    for (MetricPair metricPair : dailyMetricPairs) {
                        Iterator<Integer> iterator = areas.iterator();
                        System.out.println("Start: " + prevLatLng.getLatitude() + "," +prevLatLng.getLongitude());
                        System.out.println("End: " + gpsInfo);
                        while (iterator.hasNext()) {
                            Integer currentLocation = iterator.next();
                            System.out.println("Distance: " + metricPair.getDistance());
                            Metrics metrics = metricsDAO.getMetricsForMetricLocationCampaignDriverId(Integer.parseInt(metricPair.getMetric()),
                                    currentLocation, campaignId, driverId, TableType.METRIC_DAILY.toString());
                            if(metrics == null){
                                metricsDAO.insert(Integer.parseInt(metricPair.getMetric()), currentLocation,
                                        campaignId, driverId, metricPair.getDistance(),
                                        metricPair.getTime() / areas.size(), null, null, null, TableType.METRIC_DAILY.toString());
                            }
                            else{
                                metrics.setDistance(metrics.getDistance().add(new BigDecimal(metricPair.getDistance())));
                                metrics.setTime(metrics.getTime().add(new BigDecimal(metricPair.getTime() / areas.size())));
                                metricsDAO.update(metrics, TableType.METRIC_DAILY.toString());
                            }
                        }
                    }

                    for (MetricPair metricPair : hourlyMetricPairs) {
                        Iterator<Integer> iterator = areas.iterator();
                        while (iterator.hasNext()) {
                            Integer currentLocation = iterator.next();
                            Metrics metrics = metricsDAO.getMetricsForMetricLocationCampaignDriverId(Integer.parseInt(metricPair.getMetric()),
                                    currentLocation, campaignId, driverId, TableType.METRIC_HOURLY.toString());
                            if(metrics == null){
                                metricsDAO.insert(Integer.parseInt(metricPair.getMetric()), currentLocation,
                                        campaignId, driverId, metricPair.getDistance(),
                                        metricPair.getTime() / areas.size(), null, null, null, TableType.METRIC_HOURLY.toString());
                            }
                            else{
                                metrics.setDistance(metrics.getDistance().add(new BigDecimal(metricPair.getDistance())));
                                metrics.setTime(metrics.getTime().add(new BigDecimal(metricPair.getTime()/ areas.size())));
                                metricsDAO.update(metrics, TableType.METRIC_HOURLY.toString());
                            }
                        }
                    }

                    Iterator<Integer> iterator = areas.iterator();
                    while (iterator.hasNext()) {
                        Integer currentLocation = iterator.next();
                        DateTime end = new DateTime(gpsInfo.getTime());
                        DateTime start = new DateTime(prevTime);
                        Double timeInSec = (end.getMillis() - start.getMillis()) / 1000.0;
                        Metrics metrics = metricsDAO.getMetricsForLocationCampaignDriverId(currentLocation,
                                campaignId, driverId, TableType.METRIC_TILL_DATE.toString());
                        if(metrics == null){
                            metricsDAO.insertWithoutMetric(currentLocation,
                                    campaignId, driverId, distance,
                                    timeInSec / areas.size(), null, null, null, TableType.METRIC_TILL_DATE.toString());
                        }
                        else{
                            metrics.setDistance(metrics.getDistance().add(new BigDecimal(distance)));
                            metrics.setTime(metrics.getTime().add(new BigDecimal(timeInSec/areas.size())));
                            metricsDAO.update(metrics, TableType.METRIC_TILL_DATE.toString());
                        }
                    }



                    prevBoundary = boundary;
                    prevLatLng = new LatLng(gpsInfo.getLatitude(), gpsInfo.getLongitude());
                    prevTime = gpsInfo.getTime();
                }
            }
            checkpointDAO.update(device.getId(), gpsInfos.get(gpsInfos.size() - 1).getTime());

        }
        handle.close();
    }

    public static OsmClient getOsmClient() {
        Injector injector = Guice.createInjector(new OsmClientModule());
        OsmClient osmClient = injector.getInstance(OsmClient.class);
        return osmClient;
    }

    public static Boundaries findBiggestMatchingBoundary(List<Boundaries> boundariesList, Coordinate coordinate) {
        for (Boundaries boundary : boundariesList) {
            List<Coordinate> coordinateList = boundary.getCoordinates();
            Coordinate currentCoordinate = new Coordinate(coordinate.getLatitude(), coordinate.getLongitude());
            boolean isPresent = MapUtils.coordinateInArea(coordinateList, currentCoordinate);
            if (isPresent)
                return boundary;
        }
        return null;
    }

}
