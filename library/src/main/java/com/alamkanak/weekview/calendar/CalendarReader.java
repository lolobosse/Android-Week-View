package com.alamkanak.weekview.calendar;

import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by laurentmeyer on 15/06/15.
 */
public class CalendarReader {

    /**
     * Basic method to be called from the {@link com.alamkanak.weekview.WeekView.MonthChangeListener} to have all calendar events during one period.
     * This method is synchron --> It can be a bit long to get the view (it could lag). That's why I would use it only to test your feature.
     * For production, I would prefer the one with callback (defined in {@link CalendarInterface}): #getAllEventsWithCallback
     *
     * @param year:  provided by {@link com.alamkanak.weekview.WeekView.MonthChangeListener}
     * @param month: provided by {@link com.alamkanak.weekview.WeekView.MonthChangeListener}
     * @return events that the {@link com.alamkanak.weekview.WeekView.MonthChangeListener} should return
     */
    public static List<? extends WeekViewEvent> getAllEvents(Context context, int year, int month) {
        // Takes all the events available


        // TODO: Read the duration and the recursion of an event. Parse it will be a bit complicated but some example are existing on internet

        String[] arrayProperties = new String[]{CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.EVENT_LOCATION, CalendarContract.Events.IS_ORGANIZER,
                CalendarContract.Events.UID_2445, CalendarContract.Events.STATUS, CalendarContract.Events.ORGANIZER, CalendarContract.Events.ALL_DAY,
                CalendarContract.Events.AVAILABILITY, CalendarContract.Events.EVENT_TIMEZONE, CalendarContract.Events.RDATE, CalendarContract.Events.RRULE};
        // To retrieve the properties easily
        List<String> askedProperties = Arrays.asList(arrayProperties);
        String[] recursionTest = new String[]{CalendarContract.Events.RRULE, CalendarContract.Events.RDATE, CalendarContract.Events.EXDATE, CalendarContract.Events.EXRULE};
        // TODO: Write the recursion function because not testable offline
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(year, month - 1, 1, 0, 0, 0);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(year, month - 1, 1, 23, 59, 59);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        long startDate = startCalendar.getTimeInMillis();
        long endDate = endCalendar.getTimeInMillis();

        //TODO: Manage edge case where startDate is in the calendar but not the end date --> Should be in the calendar as well
        Cursor cursor = context.getContentResolver()
                .query(
                        CalendarContract.Events.CONTENT_URI,
                        arrayProperties, CalendarContract.Events.DTSTART + " >= " + startDate + " AND " + CalendarContract.Events.DTEND + " <= " + endDate,
                        null, null);
        ArrayList<WeekViewEvent> list = new ArrayList<>();
        Log.e("Calendar Reader", "--------------------------NEW Session " + Calendar.getInstance().getTime().toString() + "--------------------------");
        while (cursor.moveToNext()) {
            Log.d("CalendarReader", "cursor.getPosition():" + cursor.getPosition());
            CursorAsker ca = new CursorAsker(cursor, askedProperties);
            Calendar startTime = convertTimeInHumanReadable(ca.get(CalendarContract.Events.DTSTART), ca.get(CalendarContract.Events.EVENT_TIMEZONE));
            Calendar endTime = convertTimeInHumanReadable(ca.get(CalendarContract.Events.DTEND), ca.get(CalendarContract.Events.EVENT_TIMEZONE));
            if (endTime == null || startTime == null) {

            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < arrayProperties.length; i++) {
                    if (i == 3 || i == 4) {
                        sb.append(arrayProperties[i] + " = " + convertTimeInHumanReadable(cursor.getString(i), cursor.getString(12)) + "\n");
                    } else {
                        sb.append(arrayProperties[i] + " = " + cursor.getString(i) + "\n");
                    }
                    sb.append("\n\n");
                    Log.d("CalendarReader", sb.toString());
                }
                if (startTime.after(startCalendar) && endTime.before(endCalendar)) {
                    // Sample with the base constructor
                    WeekViewEvent event = new WeekViewEvent(cursor.getPosition(), cursor.getString(1), startTime, endTime);
                    // Extended test not fully exhaustive yet because of the incomplete constructor.
                    CalendarWeekViewEvent cevent = new CalendarWeekViewEvent(cursor.getPosition(), ca.get(CalendarContract.Events.TITLE),
                            convertTimeInHumanReadable(ca.get(CalendarContract.Events.DTSTART), ca.get(CalendarContract.Events.EVENT_TIMEZONE)),
                            convertTimeInHumanReadable(ca.get(CalendarContract.Events.DTEND), ca.get(CalendarContract.Events.EVENT_TIMEZONE)),
                            ca.get(CalendarContract.Events.CALENDAR_ID), ca.get(CalendarContract.Events.DESCRIPTION));
                    list.add(cevent);
                }
            }
        }
//        String[] calendarProperties = {CalendarContract.Calendars.NAME, CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL};
//
//        Cursor calendarCursor = context.getContentResolver()
//                .query(
//                        CalendarContract.Calendars.CONTENT_URI,
//                        calendarProperties, null,
//                        null, null);
//
//        while (calendarCursor.moveToNext()) {
//            for (int i = 0; i < calendarProperties.length; i++) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(calendarProperties[i] + " = " + calendarCursor.getString(i));
//                Log.d("CalendarReader", sb.toString() + "\n");
//            }
//        }
        return list;

    }

    /**
     * @param context
     * @param year
     * @param month
     * @param callback
     */
    public static void getAllEventsWithCallback(Context context, int year, int month, CalendarInterface callback) {
        return;
    }

    private static Calendar convertTimeInHumanReadable(String Slong, String timeZone) {
        if ((Slong == null)) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            if (timeZone != null) {
                calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
            }
            calendar.setTimeInMillis(Long.parseLong(Slong));
            return calendar;
        }
    }

    private static class CursorAsker {
        Cursor c;
        List<String> keys;
        private CursorAsker (Cursor c, List<String> keys){
            this.c = c;
            this.keys = keys;
        }

        protected String get(String s){
            return c.getString(keys.indexOf(s));
        }
    }
    // TODO Select the interesting calendar and try to filter silly and useless events if possible like the number of the weeks
}
