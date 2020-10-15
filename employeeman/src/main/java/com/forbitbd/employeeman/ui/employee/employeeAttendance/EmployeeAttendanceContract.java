package com.forbitbd.employeeman.ui.employee.employeeAttendance;


import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;

import java.util.List;

import okhttp3.ResponseBody;

public interface EmployeeAttendanceContract {

    interface Presenter{
        void startAddAttendanceActivity();
        void getMonthlyEmployeeAttendance(String projectId, int year, int month);
        void downloadMonthlyEmployeeAttendance(String projectId, int year, int month);
    }

    interface View{
        void startAddEmployeeAttendanceActivity();
        void showDialog();
        void hideDialog();
        void openFile(String path);
        void showToast(String path);
        void renderAdapter(List<EmployeeAttendanceResponse> attendanceResponseList);
        void startDailyAttendanceActivity(EmployeeAttendanceResponse attendanceResponse);
        String saveFile(ResponseBody responseBody, int year, int month);
    }
}
