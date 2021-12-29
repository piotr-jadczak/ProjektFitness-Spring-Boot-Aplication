package app.fitness.FitnessApp.domain;

import app.fitness.FitnessApp.domain.extra.DayOfWeekExtension;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

@Embeddable
public class RegularDate {

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
        return "dzień: " + DayOfWeekExtension.getStringValue(dayOfWeek.getValue()) + ", rozpoczęcie: " + getStartTime() + ", zakończenie: " + getEndTime();
    }
}
