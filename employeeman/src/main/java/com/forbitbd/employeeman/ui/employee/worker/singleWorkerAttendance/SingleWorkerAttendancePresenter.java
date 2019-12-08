package com.forbitbd.employeeman.ui.employee.worker.singleWorkerAttendance;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.Attendance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleWorkerAttendancePresenter implements SingleWorkerAttendanceContract.Presenter {

    private SingleWorkerAttendanceContract.View mView;

    public SingleWorkerAttendancePresenter(SingleWorkerAttendanceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getMonthlyAttendance(String projectId, String workerId, int year, int month) {
        mView.showDialog();

        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.getSingleWorkerMonthlyAttendance(projectId,workerId,year,month)
                .enqueue(new Callback<List<Attendance>>() {
                    @Override
                    public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                        mView.hideDialog();
                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Attendance>> call, Throwable t) {
                        mView.hideDialog();
                    }
                });
    }
}
