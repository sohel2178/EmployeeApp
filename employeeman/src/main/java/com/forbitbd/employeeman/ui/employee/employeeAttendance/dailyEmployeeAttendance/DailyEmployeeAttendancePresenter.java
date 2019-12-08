package com.forbitbd.employeeman.ui.employee.employeeAttendance.dailyEmployeeAttendance;



import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyEmployeeAttendancePresenter implements DailyEmployeeAttendanceContract.Presenter {

    private DailyEmployeeAttendanceContract.View mView;


    public DailyEmployeeAttendancePresenter(DailyEmployeeAttendanceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getDailyEmployeeAttendance(String projectid, EmployeeAttendanceResponse attendanceResponse) {

        mView.showDialog();
        EmployeeClient apiClient = ServiceGenerator.createService(EmployeeClient.class);
        EmployeeAttendanceResponse.ID id = attendanceResponse.get_id();

        apiClient.getDailyEmployeeAttendance(projectid,id.getYear(),id.getMonth(),id.getDay())
                .enqueue(new Callback<List<EmployeeAttendanceResponse>>() {
                    @Override
                    public void onResponse(Call<List<EmployeeAttendanceResponse>> call, Response<List<EmployeeAttendanceResponse>> response) {
                        mView.hideDialog();
                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EmployeeAttendanceResponse>> call, Throwable t) {
                        mView.hideDialog();
                    }
                });





    }
}
