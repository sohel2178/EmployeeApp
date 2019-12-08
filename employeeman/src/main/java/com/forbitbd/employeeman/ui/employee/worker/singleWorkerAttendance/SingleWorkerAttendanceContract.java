package com.forbitbd.employeeman.ui.employee.worker.singleWorkerAttendance;



import com.forbitbd.employeeman.models.Attendance;

import java.util.List;

public interface SingleWorkerAttendanceContract {
    interface Presenter{
        void getMonthlyAttendance(String projectId, String workerId, int year, int month);
    }

    interface View{
        void showDialog();
        void hideDialog();
        void renderOvertime(String overtimeTotal);
        void renderPresentCount(String presentCount);
        void renderAdapter(List<Attendance> attendanceList);
        void updateState();
    }
}
