package com.sseance.cwa.checkworkattendance.bean;

public class User {

    private String name;

    private String number;

    private String leave;

    private String leaveDate;

    private Integer rest;

    private String restDate;

    private Integer workDay;

    private Integer realWorkDay;

    private Integer absenteeismDay;

    private String timeNotes;

    private Integer forgetNumber;

    public Integer getForgetNumber() {
        return forgetNumber;
    }

    public void setForgetNumber(Integer forgetNumber) {
        this.forgetNumber = forgetNumber;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getTimeNotes() {
        return timeNotes;
    }

    public void setTimeNotes(String timeNotes) {
        this.timeNotes = timeNotes;
    }

    public Integer getAbsenteeismDay() {
        return absenteeismDay;
    }

    public void setAbsenteeismDay(Integer absenteeismDay) {
        this.absenteeismDay = absenteeismDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Integer getRest() {
        return rest;
    }

    public void setRest(Integer rest) {
        this.rest = rest;
    }

    public String getRestDate() {
        return restDate;
    }

    public void setRestDate(String restDate) {
        this.restDate = restDate;
    }

    public Integer getWorkDay() {
        return workDay;
    }

    public void setWorkDay(Integer workDay) {
        this.workDay = workDay;
    }

    public Integer getRealWorkDay() {
        return realWorkDay;
    }

    public void setRealWorkDay(Integer realWorkDay) {
        this.realWorkDay = realWorkDay;
    }
}
