package com.forbitbd.employeeman.ui.employee.workerAttendance.dailyWorkerAttendance;


import com.forbitbd.employeeman.models.AttendanceResponse;

import java.util.List;

public interface DailyWorkerAttendanceContract {

    interface Presenter{
        void getDailyAttendance(String projectid, AttendanceResponse attendanceResponse);
    }

    interface View{
        void showDialog();
        void hideDialog();
        void renderAdapter(List<AttendanceResponse> attendanceResponseList);
    }
}
