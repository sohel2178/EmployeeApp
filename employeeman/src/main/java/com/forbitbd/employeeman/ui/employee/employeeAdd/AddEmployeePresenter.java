package com.forbitbd.employeeman.ui.employee.employeeAdd;



import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.Employee;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        mView.startImagePicker();
    }

    @Override
    public boolean validate(Employee employee) {
        mView.clearPreError();

        /*if(employee.getUser()==null){
            mView.showErrorToast("Please Select a User");
            return false;
        }*/
        if(employee.getName().equals("")){
            mView.showValidationError("Name Should Not Empty",1);
            return false;
        }

        if(employee.getContact().equals("")){
            mView.showValidationError("Contact Should Not Empty",2);
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
    public void saveEmployee(Employee employee,byte[] bytes) {

        mView.showProgressDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), employee.getName());
        RequestBody contact_no = RequestBody.create(MediaType.parse("text/plain"), employee.getContact());
        RequestBody designation = RequestBody.create(MediaType.parse("text/plain"), employee.getDesignation());
        RequestBody salary = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(employee.getSalary()));


        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("contact", contact_no);
        map.put("designation", designation);
        map.put("salary", salary);

        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.addEmployee(employee.getProject(),part,map)
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
    public void updateEmployee(Employee employee,byte[] bytes) {
        mView.showProgressDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), employee.getName());
        RequestBody contact_no = RequestBody.create(MediaType.parse("text/plain"), employee.getContact());
        RequestBody designation = RequestBody.create(MediaType.parse("text/plain"), employee.getDesignation());
        RequestBody salary = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(employee.getSalary()));


        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("contact", contact_no);
        map.put("designation", designation);
        map.put("salary", salary);


        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        client.updateEmployee(employee.getProject(),employee.get_id(),part,map)
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
                            //mView.finishedAndSendEmployee(response.body());
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
