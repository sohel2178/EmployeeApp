package com.forbitbd.employeeman.ui.employee.worker;


import android.util.Log;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.Worker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerPresenter implements WorkerContract.Presenter {

    private WorkerContract.View mView;

    public WorkerPresenter(WorkerContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestWorkers(String projectId) {
        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);
        mView.showProgressDialog();
        client.getProjectWorkers(projectId)
                .enqueue(new Callback<List<Worker>>() {
                    @Override
                    public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            if(response.code()==200){
                                mView.addWorkersToAdapter(response.body());
                            }else {
                                mView.showToast("Error in Fetching Data");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Worker>> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });

    }

    @Override
    public void startAddWorkerActivity(Project project) {
        mView.startAddWorkerActivity(project);
    }

    @Override
    public void startAddAttendanceActivity(Project project) {
        mView.startAddAttendanceActivity(project);
    }

    @Override
    public void deleteWorker(Worker worker) {
        Log.d("YYYYYYY","Delete Worker Called");
        mView.showProgressDialog();

        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);
        client.deleteWorker(worker.getProject(),worker.get_id())
                .enqueue(new Callback<Worker>() {
                    @Override
                    public void onResponse(Call<Worker> call, Response<Worker> response) {
                        mView.hideProgressDialog();
                        if(response.isSuccessful()){
                            mView.removeWorkerFromAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Worker> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }
}
