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

    @Override
    public String toString() {
        return day + " " + startTime + "-" + endTime;
    }
}