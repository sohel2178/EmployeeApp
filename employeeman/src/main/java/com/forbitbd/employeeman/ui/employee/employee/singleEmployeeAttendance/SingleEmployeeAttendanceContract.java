package com.forbitbd.employeeman.ui.employee.employee.singleEmployeeAttendance;


import com.forbitbd.employeeman.models.Attendance;

import java.util.List;

public interface SingleEmployeeAttendanceContract {

    interface Presenter{
        void getMonthlyEmployeeAttendance(String projectId, String employeeId, int year, int month);
    }

    interface View{
        void showDialog();
        void hideDialog();
        void renderPresentCount(String presentCount);
        void renderAbsentCount(String absentCount);
        void renderLeaveCount(String leaveCount);
        void renderAdapter(List<Attendance> attendanceList);
        void updateState();
    }
}
