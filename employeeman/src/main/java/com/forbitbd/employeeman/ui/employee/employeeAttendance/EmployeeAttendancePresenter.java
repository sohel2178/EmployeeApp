package com.forbitbd.employeeman.ui.employee.employeeAttendance;



import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAttendancePresenter implements EmployeeAttendanceContract.Presenter {

    private EmployeeAttendanceContract.View mView;

    public EmployeeAttendancePresenter(EmployeeAttendanceContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void startAddAttendanceActivity() {
        mView.startAddEmployeeAttendanceActivity();

    }

    @Override
    public void getMonthlyEmployeeAttendance(String projectId, int year, int month) {
        mView.showDialog();
        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.getEmployeeMonthlyAttendance(projectId,year,month)
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
                /*.enqueue(new Callback<List<AttendanceResponse>>() {
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
                });*/

    }

    @Override
    public void downloadMonthlyEmployeeAttendance(String projectId, final int year, final int month) {
        mView.showDialog();

        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.getEmployeeMonthlyAttendanceFile(projectId,year,month)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        mView.hideDialog();

                        if(response.isSuccessful()){
                            String path = mView.saveFile(response.body(),year,month);

                            if(path!=null){
                                mView.openFile(path);
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
