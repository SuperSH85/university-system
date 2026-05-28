package model.course;

import java.time.*;

public class CourseTime {
    private final DayOfWeek day;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public CourseTime(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public boolean overlaps(CourseTime other) {
        if (this.day != other.day) return false;
        return this.startTime.isBefore(other.endTime) &&
                other.startTime.isBefore(this.endTime);
    }
    @Override
    public String toString() {
        return day + " " + startTime + "-" + endTime;
    }
}