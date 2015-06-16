package com.alamkanak.weekview.calendar;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.List;

/**
 * Created by laurentmeyer on 15/06/15.
 */
public abstract class CalendarInterface {
    void onDataLoaded(List<? extends WeekViewEvent> events){
        getWeekView().notifyDatasetChanged();
    }
    abstract WeekView getWeekView();
}
