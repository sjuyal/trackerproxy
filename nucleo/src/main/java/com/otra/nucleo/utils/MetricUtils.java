package com.otra.nucleo.utils;

import com.otra.dataaccess.entities.MetricPair;
import com.otra.nucleo.exceptions.OtraException;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juyal.shashank on 08/12/16.
 */
public class MetricUtils {
    public static List<MetricPair> findMonthlyMetricPairs(Double distance, DateTime startTime, DateTime endTime) throws Exception {
        List<MetricPair> metricPairs = new ArrayList<MetricPair>();
        Double timeInSec = (endTime.getMillis() - startTime.getMillis()) / 1000.0;
        Double speed = distance / timeInSec;
        String startMonth = TimeUtils.formattedDate(startTime.toLocalDateTime().toString(), "yyyyMM");
        String endMonth = TimeUtils.formattedDate(endTime.toLocalDateTime().toString(), "yyyyMM");
        if (startMonth.equalsIgnoreCase(endMonth)) {
            metricPairs.add(new MetricPair(startMonth, distance, timeInSec));
            return metricPairs;
        } else {
            Integer months = Months.monthsBetween(startTime, endTime).getMonths();
            if (months > 1)
                throw new OtraException("Difference in month too large. Cant be greater than 1");
            DateTime endOfMonth = startTime.plusMonths(1).withDayOfMonth(1).withTimeAtStartOfDay().minusMillis(1);
            timeInSec = (endOfMonth.getMillis() - startTime.getMillis()) / 1000.0;
            distance = speed * timeInSec;
            String month = TimeUtils.formattedDate(startTime.toLocalDateTime().toString(), "yyyyMM");
            metricPairs.add(new MetricPair(month, distance, timeInSec));

            //DateTime startOfNextMonth = endOfMonth.plusMillis(1);
            System.out.println(endOfMonth + "--" + endTime);
            timeInSec = (endTime.getMillis() - endOfMonth.getMillis()) / 1000.0;
            distance = speed * timeInSec;
            month = TimeUtils.formattedDate(endTime.toLocalDateTime().toString(), "yyyyMM");
            metricPairs.add(new MetricPair(month, distance, timeInSec));
            return metricPairs;
        }
    }

    public static List<MetricPair> findDailyMetricPairs(Double distance, DateTime startTime, DateTime endTime) throws Exception {
        System.out.println(startTime);
        System.out.println(endTime);
        List<MetricPair> metricPairs = new ArrayList<MetricPair>();
        Double timeInSec = (endTime.getMillis() - startTime.getMillis()) / 1000.0;
        Double speed = distance / timeInSec;
        String startDay = TimeUtils.formattedDate(startTime.toLocalDateTime().toString(), "yyyyMMdd");
        String endDay = TimeUtils.formattedDate(endTime.toLocalDateTime().toString(), "yyyyMMdd");
        if (startDay.equalsIgnoreCase(endDay)) {
            metricPairs.add(new MetricPair(startDay, distance, timeInSec));
            return metricPairs;
        } else {
            Integer days = Days.daysBetween(startTime, endTime).getDays();
            if (days > 1)
                throw new OtraException("Difference in days too large. Cant be greater than 1");
            DateTime endOfDay = startTime.plusDays(1).withTimeAtStartOfDay().minusMillis(1);
            timeInSec = (endOfDay.getMillis() - startTime.getMillis()) / 1000.0;
            distance = speed * timeInSec;
            String day = TimeUtils.formattedDate(startTime.toLocalDateTime().toString(), "yyyyMMdd");
            metricPairs.add(new MetricPair(day, distance, timeInSec));

            //DateTime startOfNextMonth = endOfMonth.plusMillis(1);
            System.out.println(endOfDay + "--" + endTime);
            timeInSec = (endTime.getMillis() - endOfDay.getMillis()) / 1000.0;
            distance = speed * timeInSec;
            day = TimeUtils.formattedDate(endTime.toLocalDateTime().toString(), "yyyyMMdd");
            metricPairs.add(new MetricPair(day, distance, timeInSec));
            return metricPairs;
        }
    }

    public static List<MetricPair> findHourlyMetricPairs(Double distance, DateTime startTime, DateTime endTime) throws Exception {
        List<MetricPair> metricPairs = new ArrayList<MetricPair>();
        Double timeInSec = (endTime.getMillis() - startTime.getMillis()) / 1000.0;
        Double speed = distance / timeInSec;
        String startHour = TimeUtils.formattedDate(startTime.toLocalDateTime().toString(), "yyyyMMddHH");
        String endHour = TimeUtils.formattedDate(endTime.toLocalDateTime().toString(), "yyyyMMddHH");
        if (startHour.equalsIgnoreCase(endHour)) {
            metricPairs.add(new MetricPair(startHour, distance, timeInSec));
            return metricPairs;
        } else {
            Integer hours = Hours.hoursBetween(startTime, endTime).getHours();
            if (hours > 1)
                throw new OtraException("Difference in hours too large. Cant be greater than 1");
            DateTime endOfHour = startTime.plusHours(1).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).minusMillis(1);
            timeInSec = (endOfHour.getMillis() - startTime.getMillis()) / 1000.0;
            distance = speed * timeInSec;
            String hour = TimeUtils.formattedDate(startTime.toLocalDateTime().toString(), "yyyyMMddHH");
            metricPairs.add(new MetricPair(hour, distance, timeInSec));

            //DateTime startOfNextMonth = endOfMonth.plusMillis(1);
            timeInSec = (endTime.getMillis() - endOfHour.getMillis()) / 1000.0;
            distance = speed * timeInSec;
            hour = TimeUtils.formattedDate(endTime.toLocalDateTime().toString(), "yyyyMMddHH");
            metricPairs.add(new MetricPair(hour, distance, timeInSec));
            return metricPairs;
        }
    }
}
