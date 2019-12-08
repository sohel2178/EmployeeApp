package com.forbitbd.employeeman.ui.employee.workerAttendance.attendance;


import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.Worker;

import java.util.Date;
import java.util.List;

public interface AttendanceContract {

    interface Presenter{
        void openCalendar();
        void saveClick();
        void uploadAttendanceToServer(List<Worker> workerList, Date date, String projectId);
        void getAllWorkers(String projectId);
    }

    interface View{
        void openCalendar();
        void saveClick();
        void renderWorkerList(List<Worker> workerList);
        void showDialog();
        void hideDialog();
        void back(AttendanceResponse attendanceResponse);
        void showToast(String message);
    }
}
