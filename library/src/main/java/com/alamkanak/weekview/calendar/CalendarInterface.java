package com.alamkanak.weekview.calendar;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.List;

/**
 * Created by laurentmeyer on 15/06/15.
 */
public interface CalendarInterface {
    void onDataLoaded(List<? extends WeekViewEvent> events);
}
