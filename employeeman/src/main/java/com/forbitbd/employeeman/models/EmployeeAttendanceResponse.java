package com.forbitbd.employeeman.models;

import java.io.Serializable;

public class EmployeeAttendanceResponse implements Serializable {

    private ID _id;
    private int count;
    private int total_present;
    private int total_absent;
    private int total_leave;
    private Employee employee;


    public EmployeeAttendanceResponse() {
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
}
