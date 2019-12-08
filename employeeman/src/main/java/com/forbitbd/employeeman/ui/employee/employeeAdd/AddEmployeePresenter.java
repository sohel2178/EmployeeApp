package com.forbitbd.employeeman.ui.employee.employeeAdd;



import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.Employee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeePresenter implements AddEmployeeContract.Presenter {

    private AddEmployeeContract.View mView;

    public AddEmployeePresenter(AddEmployeeContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void browseClick() {
        mView.startSearchUserActivity();
    }

    @Override
    public boolean validate(Employee employee) {
        mView.clearPreError();

        if(employee.getUser()==null){
            mView.showErrorToast("Please Select a User");
            return false;
        }


        if(employee.getSalary()<=0){
            mView.showValidationError("Salary Should Greater Than Zero",4);
            return false;
        }

        if(employee.getDesignation().equals("") || employee.getDesignation()==null){
            mView.showValidationError("Empty Field is not Allowed",5);
            return false;
        }

        return true;
    }

    @Override
    public void saveEmployee(Employee employee) {

        mView.showProgressDialog();

        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.addEmployee(employee.getProject(),employee)
                .enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){

                            if(response.code()==201){
                                mView.finishedAndSendEmployee(response.body());
                            }else if(response.code()==300){
                                mView.showErrorToast("Employee with Same name Already Exist in this Project");
                            }else{
                                mView.showErrorToast("Error Happened in Saving Worker");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        mView.hideProgressDialog();

                    }
                });

    }

    @Override
    public void updateEmployee(Employee employee) {
        mView.showProgressDialog();
        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.updateEmployee(employee.getProject(),employee.get_id(),employee)
                .enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        mView.hideProgressDialog();
                        if(response.isSuccessful()){
                            mView.finishedAndSendEmployee(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void checkAndSave() {
        mView.checkAndSave();
    }

    @Override
    public void bind() {
        mView.bind();
    }
}
