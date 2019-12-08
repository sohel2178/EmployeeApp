package com.forbitbd.employeeman.ui.employee.employeeAttendance.employeeAddAttendance;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.AttendanceBody;
import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.Employee;
import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;
import com.forbitbd.employeeman.models.Worker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAddAttendancePresenter implements EmployeeAddAttendanceContract.Presenter {

    private EmployeeAddAttendanceContract.View mView;

    public EmployeeAddAttendancePresenter(EmployeeAddAttendanceContract.View mView) {
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
    public void uploadEmployeeAttendanceToServer(List<Employee> employeeList, Date date, String projectId) {
        EmployeeClient apiClient = ServiceGenerator.createService(EmployeeClient.class);

        Gson gson = new Gson();
        String workers = gson.toJson(getFilteredList(employeeList));

        AttendanceBody body = new AttendanceBody();
        body.setDate(date);
        body.setWorkers(workers);

        mView.showDialog();

        apiClient.postEmployeeAttendance(body,projectId)
                .enqueue(new Callback<EmployeeAttendanceResponse>() {
                    @Override
                    public void onResponse(Call<EmployeeAttendanceResponse> call, Response<EmployeeAttendanceResponse> response) {
                        mView.hideDialog();

                        if(response.isSuccessful()){
                            mView.back(response.body());
                        }else if(response.code()==440){
                            mView.showToast("Already Inserted for this Date");
                        }
                    }

                    @Override
                    public void onFailure(Call<EmployeeAttendanceResponse> call, Throwable t) {
                        mView.hideDialog();
                    }
                });

    }

    @Override
    public void getAllProjectEmployee(String projectId) {
        mView.showDialog();
        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);
        client.getProjectEmployees(projectId)
                .enqueue(new Callback<List<Employee>>() {
                    @Override
                    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                        mView.hideDialog();

                        if(response.isSuccessful()){
                            mView.renderEmployeeList(response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Employee>> call, Throwable t) {
                        mView.hideDialog();

                    }
                });
    }

    private List<Worker> getFilteredList(List<Employee> employeeList){
        List<Worker> tempList= new ArrayList<>();

        for(Employee x: employeeList){
            Worker worker = new Worker();
            worker.set_id(x.get_id());
            worker.setStatus(x.getStatus());
            tempList.add(worker);
        }
        return tempList;
    }
}
