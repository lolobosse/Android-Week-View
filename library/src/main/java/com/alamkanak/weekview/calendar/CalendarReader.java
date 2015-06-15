package com.alamkanak.weekview.calendar;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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

        String[] arrayProperties = new String[]{"calendar_id", "title", "description",
                "dtstart", "dtend", "eventLocation", "isOrganizer", "uid2445", "eventStatus", "organizer", "allDay", "availability", "eventTimezone", "calendar_id"};

        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        arrayProperties, null,
                        null, null);
        Log.e("Calendar Reader", "--------------------------NEW Session " + Calendar.getInstance().getTime().toString() + "--------------------------");
        while (cursor.moveToNext()) {
            if (cursor.getPosition() > 480 && cursor.getPosition() < 500) {
                Log.d("CalendarReader", "cursor.getPosition():" + cursor.getPosition());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < arrayProperties.length; i++) {
                    if (i == 3 || i == 4) {
                        sb.append(arrayProperties[i] + " = " + convertTimeInHumanReadable(cursor.getString(i), cursor.getString(12)) + "\n");
                    } else {
                        sb.append(arrayProperties[i] + " = " + cursor.getString(i) + "\n");
                    }
                }
                sb.append("\n\n");
                Log.d("CalendarReader", sb.toString());
            }
        }
        return new ArrayList<>();
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

    private static String convertTimeInHumanReadable(String Slong, String timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
        calendar.setTimeInMillis(Long.parseLong(Slong));
        return calendar.getTime().toString();
    }

    // TODO Select the interesting calendar and try to filter dumm events if possible like the number of the weeks
}
