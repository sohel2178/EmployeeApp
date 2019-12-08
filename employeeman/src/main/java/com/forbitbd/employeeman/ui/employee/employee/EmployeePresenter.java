package com.forbitbd.employeeman.ui.employee.employee;


import android.util.Log;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeePresenter implements EmployeeContract.Presenter {

    private EmployeeContract.View mView;

    public EmployeePresenter(EmployeeContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestProjectEmployees(String projectId) {
        mView.showProgressDialog();
        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.getProjectEmployees(projectId)
                .enqueue(new Callback<List<Employee>>() {
                    @Override
                    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Employee>> call, Throwable t) {
                        mView.hideProgressDialog();

                    }
                });
    }

    @Override
    public void deleteEmployee(Employee employee) {
        mView.showProgressDialog();

        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);
        client.deleteEmployee(employee.getProject(),employee.get_id())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mView.hideProgressDialog();
                        if(response.isSuccessful()){
                            mView.removeEmployeeFromAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
               /* .enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        mView.hideProgressDialog();
                        Log.d("HHHHHHHH","Successfull");
                        if(response.isSuccessful()){
                            mView.removeEmployeeFromAdapter(response.body());
                            Log.d("HHHHHHHH","Inside Success");
                        }
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        mView.hideProgressDialog();
                        Log.d("HHHHHHHH","Error "+t.getMessage());
                    }
                });*/
    }

    @Override
    public void startAddEmployeeActivity(Project project) {
        mView.startAddEmployeeActivity(project);

    }

    @Override
    public void startAddEmployeeAttendanceActivity(Project project) {
        mView.startAddEmployeeAttendanceActivity(project);
    }
}
