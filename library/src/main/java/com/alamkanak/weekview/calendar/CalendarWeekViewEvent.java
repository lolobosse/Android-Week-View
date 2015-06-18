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

    public CalendarWeekViewEvent(long id, String name, Calendar startTime, Calendar endTime, String calendarId, String description) {
        super(id, name, startTime, endTime);
        this.calendarId = calendarId;
        this.description = description;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGuestCanModify() {
        return guestCanModify;
    }

    public void setGuestCanModify(boolean guestCanModify) {
        this.guestCanModify = guestCanModify;
    }

    public boolean isGuestCanSeeOtherGuests() {
        return guestCanSeeOtherGuests;
    }

    public void setGuestCanSeeOtherGuests(boolean guestCanSeeOtherGuests) {
        this.guestCanSeeOtherGuests = guestCanSeeOtherGuests;
    }

    public boolean isGuestCanInviteOthers() {
        return guestCanInviteOthers;
    }

    public void setGuestCanInviteOthers(boolean guestCanInviteOthers) {
        this.guestCanInviteOthers = guestCanInviteOthers;
    }

    public boolean isOrganizer() {
        return isOrganizer;
    }

    public void setIsOrganizer(boolean isOrganizer) {
        this.isOrganizer = isOrganizer;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getrDate() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    public String getrRule() {
        return rRule;
    }

    public void setrRule(String rRule) {
        this.rRule = rRule;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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
