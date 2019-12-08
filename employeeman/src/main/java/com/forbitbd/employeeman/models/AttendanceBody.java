package com.forbitbd.employeeman.models;

import java.util.Date;

public class AttendanceBody {
    private Date date;
    private String workers;

    public AttendanceBody() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWorkers() {
        return workers;
    }

    public void setWorkers(String workers) {
        this.workers = workers;
    }
}
