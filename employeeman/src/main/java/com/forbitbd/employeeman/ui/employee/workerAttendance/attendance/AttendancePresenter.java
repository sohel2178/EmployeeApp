package com.forbitbd.employeeman.ui.employee.workerAttendance.attendance;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.AttendanceBody;
import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.Worker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendancePresenter implements AttendanceContract.Presenter {

    private AttendanceContract.View mView;

    public AttendancePresenter(AttendanceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void openCalendar() {
        mView.openCalendar();
    }

    @Override
    public void saveClick() {
        mView.saveClick();
    }


    @Override
    public void uploadAttendanceToServer(List<Worker> workerList, Date date, String projectId) {
        EmployeeClient apiClient = ServiceGenerator.createService(EmployeeClient.class);

        Gson gson = new Gson();
        String workers = gson.toJson(getFilteredList(workerList));

        AttendanceBody body = new AttendanceBody();
        body.setDate(date);
        body.setWorkers(workers);

        mView.showDialog();

        apiClient.postAttendance(body,projectId).enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                mView.hideDialog();
                if (response.code()==201){
                    mView.back(response.body());
                }else if (response.code()==440){
                    mView.showToast("Already Inserted for this Date");
                }
            }

            @Override
            public void onFailure(Call<AttendanceResponse> call, Throwable t) {

                mView.hideDialog();
                mView.showToast("Server Error");

            }
        });
    }

    @Override
    public void getAllWorkers(String projectId) {
        mView.showDialog();
        EmployeeClient apiClient = ServiceGenerator.createService(EmployeeClient.class);
        apiClient.getAllWorkers(projectId)
                .enqueue(new Callback<List<Worker>>() {
                    @Override
                    public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response) {
                        mView.hideDialog();
                        if(response.isSuccessful()){
                            mView.renderWorkerList(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Worker>> call, Throwable t) {
                        mView.hideDialog();
                    }
                });

    }

    private List<Worker> getFilteredList(List<Worker> workerList){
        List<Worker> tempList= new ArrayList<>();

        for(Worker x: workerList){
            Worker worker = new Worker();
            worker.set_id(x.get_id());
            worker.setStatus(x.getStatus());
            worker.setOver_time(x.getOver_time());
            tempList.add(worker);
        }
        return tempList;
    }
}
