package com.alamkanak.weekview.calendar;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.List;

/**
 * Created by laurentmeyer on 15/06/15.
 */
public class CalendarReader {

    /**
     * Basic method to be called from the {@link com.alamkanak.weekview.WeekView.MonthChangeListener} to have all calendar events during one period.
     * This method is synchron --> It can be a bit long to get the view (it could lag). That's why I would use it only to test your feature.
     * For production, I would prefer the one with callback (defined in {@link CalendarInterface}): #getAllEventsWithCallback
     * @param year: provided by {@link com.alamkanak.weekview.WeekView.MonthChangeListener}
     * @param month: provided by {@link com.alamkanak.weekview.WeekView.MonthChangeListener}
     * @return events that the {@link com.alamkanak.weekview.WeekView.MonthChangeListener} should return
     */
    public static List<? extends WeekViewEvent> getAllEvents(Context context, int year, int month) {
        // Takes all the events available
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[] { "calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation" }, null,
                        null, null);
        while (cursor.moveToNext()){

        }
        return null;
    }

    /**
     *
     * @param context
     * @param year
     * @param month
     * @param callback
     */
    public static void getAllEventsWithCallback(Context context, int year, int month, CalendarInterface callback){
        return;
    }
}
