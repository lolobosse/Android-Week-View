package com.alamkanak.weekview.calendar;

import com.alamkanak.weekview.WeekViewEvent;

/**
 * Created by laurentmeyer on 15/06/15.
 */

/**
 * This is a class based on the original {@link WeekViewEvent} but with a bunch of other data which can be provided by the calendar content provider
 * Based on the {@link android.provider.CalendarContract.EventsColumns}
 */
public class CalendarWeekViewEvent extends WeekViewEvent {
    boolean ALL_DAY;
    
}
