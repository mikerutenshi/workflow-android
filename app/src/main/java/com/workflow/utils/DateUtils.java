package com.workflow.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String TYPE_DATE_TIME = "type_date_time";
    public static final String TYPE_DATE = "type_date";

    private static final Locale indonesian = new Locale("in", "ID");
    private static final SimpleDateFormat clientDate = new SimpleDateFormat("dd MMM yyyy", indonesian);
    private static final SimpleDateFormat clientDateMonth = new SimpleDateFormat("dd MMM", indonesian);
    private static final SimpleDateFormat clientDateTime = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", indonesian);
    private static final SimpleDateFormat serverDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
    private static final SimpleDateFormat serverDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static String serverToClient(String serverDate, String dateType) {
        Date date = null;
        try {
            date = serverDateTime.parse(serverDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (dateType) {
            case TYPE_DATE_TIME:
                return clientDateTime.format(date);
            case TYPE_DATE:
                return clientDate.format(date);
            default:
                return null;
        }
    }

    public static String clientToServer(Calendar calendar, String dateType) {
        switch (dateType) {
            case TYPE_DATE_TIME:
                return serverDateTime.format(calendar.getTime());
            case TYPE_DATE:
                return serverDate.format(calendar.getTime());
            default:
                return null;
        }
    }
}
