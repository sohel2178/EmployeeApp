package com.forbitbd.employeeman.ui.employee.workerAttendance;


import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.Worker;

import java.util.List;

import okhttp3.ResponseBody;

public interface WorkerAttendanceContract {

    interface Presenter{
        void requestForAttendances(Worker worker);
        void startAddAttendanceActivity();
        void getAllAttendance(String projectId);
        void getMonthlyAttendance(String projectId, int year, int month);
        void downloadMonthlyWorkerAttendance(String projectId, int year, int month);
    }

    interface View{
        void startAddAttendanceActivity();
        void showDialog();
        void hideDialog();
        void renderAdapter(List<AttendanceResponse> attendanceResponseList);
        void startDailyAttendanceActivity(AttendanceResponse attendanceResponse);

        void openDownloadedFile(String path);
        void showToast(String path);
        String saveFile(ResponseBody responseBody, int year, int month);
    }
}
