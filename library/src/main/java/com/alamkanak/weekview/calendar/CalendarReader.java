package com.alamkanak.weekview.calendar;

import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
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

        String[] arrayProperties = new String[]{"calendar_id", "title", "description",
                "dtstart", "dtend", "eventLocation", "isOrganizer", "uid2445", "eventStatus", "organizer", "allDay", "availability", "eventTimezone", "rdate", CalendarContract.Events.RRULE};

        Cursor cursor = context.getContentResolver()
                .query(
                        CalendarContract.Events.CONTENT_URI,
                        arrayProperties, CalendarContract.Events.RRULE + " IS NOT NULL",
                        null, null);
        ArrayList<WeekViewEvent> list = new ArrayList<>();
        Log.e("Calendar Reader", "--------------------------NEW Session " + Calendar.getInstance().getTime().toString() + "--------------------------");
        while (cursor.moveToNext()) {
            Log.d("CalendarReader", "cursor.getPosition():" + cursor.getPosition());
            Calendar startTime = convertTimeInHumanReadable(cursor.getString(3), cursor.getString(12));
            Calendar endTime = convertTimeInHumanReadable(cursor.getString(4), cursor.getString(12));
            if (endTime == null || startTime == null) {

            } else {
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.set(year, month - 1, 1, 0, 0, 0);
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.set(year, month - 1, 1, 0, 0, 0);
                endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
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
                    WeekViewEvent event = new WeekViewEvent(cursor.getPosition(), cursor.getString(1), startTime, endTime);
                    list.add(event);
                }
            }
        }
        // TODO: See howto make it compatible with API <9
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

    // TODO Select the interesting calendar and try to filter dumm events if possible like the number of the weeks
}
