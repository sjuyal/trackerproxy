package com.otra.nucleo.utils;

/**
 * Created by juyal.shashank on 27/11/16.
 */

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

public class TimeUtils {
    public static String formattedDate(String time, String toFormat) {
        DateTimeFormatter dtf_inp = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").withLocale(Locale.ROOT)
                .withChronology(ISOChronology.getInstanceUTC());
        DateTime dateTime = dtf_inp.parseDateTime(time);

        return dateTime.toString(toFormat);
    }
}
