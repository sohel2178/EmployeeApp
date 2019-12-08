package com.forbitbd.employeeman.ui.employee.employeeAttendance.dailyEmployeeAttendance;


import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;

import java.util.List;

public interface DailyEmployeeAttendanceContract {

    interface Presenter{
        void getDailyEmployeeAttendance(String projectid, EmployeeAttendanceResponse attendanceResponse);
    }

    interface View{
        void showDialog();
        void hideDialog();
        void renderAdapter(List<EmployeeAttendanceResponse> attendanceResponseList);
    }
}
