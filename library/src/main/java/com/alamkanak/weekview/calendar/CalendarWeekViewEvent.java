package com.alamkanak.weekview.calendar;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.Calendar;

/**
 * Created by laurentmeyer on 15/06/15.
 */

/**
 * This is a class based on the original {@link WeekViewEvent} but with a bunch of other data which can be provided by the calendar content provider
 * Based on the {@link android.provider.CalendarContract.EventsColumns}
 */
public class CalendarWeekViewEvent extends WeekViewEvent {
    boolean allDay;
    enum Availability {BUSY, FREE, TENTATIVE}
    Availability availability;
    String calendarId;
    String description;
    boolean guestCanModify;
    boolean guestCanSeeOtherGuests;
    boolean guestCanInviteOthers;
    boolean isOrganizer;
    String organizer;
    String rDate;
    String rRule;
    String uuid;

    // Date Start and Date End are included in the parent class we just need to convert them

    public void setStartTime(long startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);
        super.setStartTime(calendar);
    }

    public void setEndTime(long endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endTime);
        super.setStartTime(calendar);
    }

    // TODO: Define converter with TimeZone because of the time coming from Google Calendar Provider

}
