package com.forbitbd.employeeman.models;

import java.io.Serializable;
import java.util.Date;

public class Worker implements Serializable {
    private String _id;
    private String name;
    private String designation;
    private String contact_no;
    private String image;
    private String project;
    private Date date_of_birth;
    private Date created_at;
    private int status;
    private double over_time;
    private double wages_rate_per_day;
    private double over_time_rate_per_hour;

    public Worker() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
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

    public double getWages_rate_per_day() {
        return wages_rate_per_day;
    }

    public void setWages_rate_per_day(double wages_rate_per_day) {
        this.wages_rate_per_day = wages_rate_per_day;
    }

    public double getOver_time_rate_per_hour() {
        return over_time_rate_per_hour;
    }

    public void setOver_time_rate_per_hour(double over_time_rate_per_hour) {
        this.over_time_rate_per_hour = over_time_rate_per_hour;
    }
}
