package com.forbitbd.employeeman.ui.employee.workerAttendance;

import android.util.Log;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.Attendance;
import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.Worker;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerAttendancePresenter implements WorkerAttendanceContract.Presenter {

    private WorkerAttendanceContract.View mView;

    public WorkerAttendancePresenter(WorkerAttendanceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestForAttendances(Worker worker) {
        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.getAttendances(worker.get_id())
                .enqueue(new Callback<List<Attendance>>() {
                    @Override
                    public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                        if(response.isSuccessful()){
                            Log.d("HHHH",response.body().size()+"");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Attendance>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void startAddAttendanceActivity() {
        mView.startAddAttendanceActivity();
    }

    @Override
    public void getAllAttendance(String projectId) {
        mView.showDialog();
        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);
        client.getAllAttendance(projectId).enqueue(new Callback<List<AttendanceResponse>>() {
            @Override
            public void onResponse(Call<List<AttendanceResponse>> call, Response<List<AttendanceResponse>> response) {
                mView.hideDialog();
                if(response.isSuccessful()){

                    mView.renderAdapter(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<AttendanceResponse>> call, Throwable t) {
                mView.hideDialog();

            }
        });

    }

    @Override
    public void getMonthlyAttendance(String projectId, int year, int month) {

        mView.showDialog();
        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.getMonthlyAttendance(projectId,year,month).enqueue(new Callback<List<AttendanceResponse>>() {
            @Override
            public void onResponse(Call<List<AttendanceResponse>> call, Response<List<AttendanceResponse>> response) {
                mView.hideDialog();
                if(response.isSuccessful()){

                    mView.renderAdapter(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<AttendanceResponse>> call, Throwable t) {
                mView.hideDialog();
            }
        });

    }

    @Override
    public void downloadMonthlyWorkerAttendance(String projectId, final int year, final int month) {
        mView.showDialog();

        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.getWorkerMonthlyAttendanceFile(projectId,year,month)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        mView.hideDialog();

                        if(response.isSuccessful()){
                            String path = mView.saveFile(response.body(),year,month);

                            if(path!=null){
                                mView.openDownloadedFile(path);
                            }else{
                                mView.showToast("Failed to Save the File");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mView.hideDialog();
                    }
                });
    }
}
