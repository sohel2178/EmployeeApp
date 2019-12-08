package com.forbitbd.employeeman.models;

import java.io.Serializable;

public class AttendanceResponse implements Serializable {

    private ID _id;
    private int count;
    private int total_present;
    private int total_absent;
    private int total_leave;
    private int total_overtime;
    private Worker worker;



    public AttendanceResponse() {

    }


    public ID get_id() {
        return _id;
    }

    public void set_id(ID _id) {
        this._id = _id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal_present() {
        return total_present;
    }

    public void setTotal_present(int total_present) {
        this.total_present = total_present;
    }

    public int getTotal_overtime() {
        return total_overtime;
    }

    public void setTotal_overtime(int total_overtime) {
        this.total_overtime = total_overtime;
    }

    public class ID implements Serializable {
        private int day;
        private int month;
        private int year;

        public ID() {
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public int getTotal_absent() {
        return total_absent;
    }

    public void setTotal_absent(int total_absent) {
        this.total_absent = total_absent;
    }

    public int getTotal_leave() {
        return total_leave;
    }

    public void setTotal_leave(int total_leave) {
        this.total_leave = total_leave;
    }
}
