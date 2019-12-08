package com.forbitbd.employeeman.ui.employee.workerAttendance.dailyWorkerAttendance;

import android.util.Log;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.AttendanceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyWorkerAttendancePresenter implements DailyWorkerAttendanceContract.Presenter {

    private DailyWorkerAttendanceContract.View mView;

    public DailyWorkerAttendancePresenter(DailyWorkerAttendanceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getDailyAttendance(String projectid, AttendanceResponse attendanceResponse) {
        mView.showDialog();
        EmployeeClient apiClient = ServiceGenerator.createService(EmployeeClient.class);

        AttendanceResponse.ID id = attendanceResponse.get_id();

        apiClient.getDailyAttendance(projectid,id.getYear(),id.getMonth(),id.getDay())
                .enqueue(new Callback<List<AttendanceResponse>>() {
                    @Override
                    public void onResponse(Call<List<AttendanceResponse>> call, Response<List<AttendanceResponse>> response) {
                        mView.hideDialog();
                        Log.d("JJJJJJ","onResponse");

                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                            Log.d("JJJJJJ",response.body().size()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AttendanceResponse>> call, Throwable t) {
                        Log.d("JJJJJJ","onFailure "+t.getMessage());
                        mView.hideDialog();
                    }
                });

    }
}
