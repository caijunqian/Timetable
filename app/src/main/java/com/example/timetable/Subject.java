package com.example.timetable;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import java.util.Arrays;
import java.util.List;

public class Subject implements ScheduleEnable {
    private Integer courseId;
    private Integer userId;
    private Integer termId;
    private String courseName;
    private Integer startWeek;
    private Integer endWeek;
    private Integer courseTimeId;
    private Integer weekday;
    private String classroom;
    private Integer startLesson;
    private Integer endLesson;

    public Subject() {
    }

    public Subject(Integer courseId, Integer userId, Integer termId, String courseName, Integer startWeek, Integer endWeek, Integer courseTimeId, Integer weekday, String classroom, Integer startLesson, Integer endLesson) {
        this.courseId = courseId;
        this.userId = userId;
        this.termId = termId;
        this.courseName = courseName;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.courseTimeId = courseTimeId;
        this.weekday = weekday;
        this.classroom = classroom;
        this.startLesson = startLesson;
        this.endLesson = endLesson;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getTermId() {
        return termId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public Integer getCourseTimeId() {
        return courseTimeId;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public String getClassroom() {
        return classroom;
    }

    public Integer getStartLesson() {
        return startLesson;
    }

    public Integer getEndLesson() {
        return endLesson;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }

    public void setCourseTimeId(Integer courseTimeId) {
        this.courseTimeId = courseTimeId;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setStartLesson(Integer startLesson) {
        this.startLesson = startLesson;
    }

    public void setEndLesson(Integer endLesson) {
        this.endLesson = endLesson;
    }


    @Override
    public String toString() {
        return "Subject{" +
                "courseId=" + courseId +
                ", userId=" + userId +
                ", termId=" + termId +
                ", courseName='" + courseName + '\'' +
                ", startWeek=" + startWeek +
                ", endWeek=" + endWeek +
                ", courseTimeId=" + courseTimeId +
                ", weekday=" + weekday +
                ", classroom='" + classroom + '\'' +
                ", startLesson=" + startLesson +
                ", endLesson=" + endLesson +
                '}';
    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule = new Schedule();
        schedule.setDay(getWeekday());
        schedule.setName(getCourseName());
        schedule.setRoom(getClassroom());
        schedule.setStart(getStartLesson());
        schedule.setStep(getEndLesson()-getStartLesson()+1);
        //生成课程上课周数组
        Integer[] weekData = new Integer[endWeek-startWeek +1];
        for(int i=0,j=startWeek;j<=endWeek;i++,j++)
            weekData[i] = i;
        List<Integer> weekList = Arrays.asList(weekData);
        schedule.setWeekList(weekList);
        schedule.setColorRandom(1);
        return schedule;
    }
}
