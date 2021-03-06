package app.fitness.FitnessApp.domain;

import app.fitness.FitnessApp.domain.extra.DayOfWeekExtension;
import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;


@Embeddable
public class RegularDate implements Comparable<RegularDate> {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public RegularDate() {
    }

    public RegularDate(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return DayOfWeekExtension.getStringValue(dayOfWeek.getValue()) + " " + getStartTime() + " - " + getEndTime();
    }

    @Override
    public int compareTo(RegularDate o) {
        return this.getDayOfWeek().getValue() - o.getDayOfWeek().getValue();
    }
}
