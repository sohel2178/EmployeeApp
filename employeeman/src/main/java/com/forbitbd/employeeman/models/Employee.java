package com.forbitbd.employeeman.models;

import com.forbitbd.androidutils.models.User;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {

    private String _id;
    private String designation;
    private String project;
    private User user;
    private Date created_at;
    private int status;
    private double salary;


    public Employee() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return user.getName();
    }


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getContact_no() {
        return this.user.getContact();
    }


    public String getImage() {
        return user.getImage();
    }


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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


    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
