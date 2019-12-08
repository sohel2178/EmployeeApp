package com.forbitbd.employeeman.models;

import java.util.Date;

public class Attendance {

    private String project;
    private String worker;
    private MyDate date;
    private Date created_at;
    private int status;
    private double over_time;

    public Attendance() {
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public MyDate getDate() {
        return date;
    }

    public void setDate(MyDate date) {
        this.date = date;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getOver_time() {
        return over_time;
    }

    public void setOver_time(double over_time) {
        this.over_time = over_time;
    }

}
