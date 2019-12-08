package com.forbitbd.employeeman.ui.employee.workerAdd;

import android.util.Log;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.employeeman.api.EmployeeClient;
import com.forbitbd.employeeman.models.Worker;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWorkerPresenter implements AddWorkerContract.Presenter {

    private AddWorkerContract.View mView;

    public AddWorkerPresenter(AddWorkerContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void browseClick() {
        mView.openCropImageActivity();
    }

    @Override
    public void bindWorker(Worker worker) {
        mView.bindWorker(worker);
    }

    @Override
    public void dateClick() {
        mView.openCalendar();
    }

    @Override
    public void checkAndSave() {
        mView.checkAndSave();
    }

    @Override
    public boolean validate(Worker worker) {
        mView.clearPreError();

        if(worker.getName().equals("") || worker.getName()==null){
            mView.showValidationError("Empty Field is not Allowed",1);
            return false;
        }

        if(worker.getContact_no().equals("") || worker.getContact_no()==null){
            mView.showValidationError("Empty Field is not Allowed",2);
            return false;
        }

        if(worker.getDesignation().equals("") || worker.getDesignation()==null){
            mView.showValidationError("Empty Field is not Allowed",4);
            return false;
        }

        if(worker.getWages_rate_per_day()<=0){
            mView.showValidationError("Must be Grater than Zero",5);
            return false;
        }

        if(worker.getOver_time_rate_per_hour()<=0){
            mView.showValidationError("Must be Grater than Zero",6);
            return false;
        }

        return true;
    }

    @Override
    public void saveWorker(Worker worker, byte[] bytes) {
        mView.showDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            //MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
            // RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), bytes);
            // Create MultipartBody.Part using file request-body,file name and part name
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), worker.getName());
        RequestBody contact_no = RequestBody.create(MediaType.parse("text/plain"), worker.getContact_no());
        RequestBody designation = RequestBody.create(MediaType.parse("text/plain"), worker.getDesignation());
        RequestBody date_of_birth = RequestBody.create(MediaType.parse("text/plain"), MyUtil.getStringDate(worker.getDate_of_birth()));
        RequestBody wages_rate_per_day = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(worker.getWages_rate_per_day()));
        RequestBody over_time_rate_per_hour = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(worker.getOver_time_rate_per_hour()));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("contact_no", contact_no);
        map.put("designation", designation);
        map.put("date_of_birth", date_of_birth);
        map.put("wages_rate_per_day", wages_rate_per_day);
        map.put("over_time_rate_per_hour", over_time_rate_per_hour);

        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);

        Call<Worker> call = client.addWorker(worker.getProject(),part,map);

        call.enqueue(new Callback<Worker>() {
            @Override
            public void onResponse(Call<Worker> call, Response<Worker> response) {

                if(response.isSuccessful()){

                    if(response.code()==201){
                        mView.finishedAndSendWorker(response.body());
                    }else if(response.code()==300){
                        mView.showErrorToast("Worker with Same name Already Exist in this Project");
                    }else{
                        mView.showErrorToast("Error Happened in Saving Worker");
                    }
                }

                mView.hideDialog();
            }

            @Override
            public void onFailure(Call<Worker> call, Throwable t) {
                Log.d("GGGGGG","Error"+t.getMessage());
                mView.hideDialog();
            }
        });
    }

    @Override
    public void updateWorker(Worker worker, byte[] bytes) {

        mView.showDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            //MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
            // RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), bytes);
            // Create MultipartBody.Part using file request-body,file name and part name
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), worker.getName());
        RequestBody contact_no = RequestBody.create(MediaType.parse("text/plain"), worker.getContact_no());
        RequestBody designation = RequestBody.create(MediaType.parse("text/plain"), worker.getDesignation());
        RequestBody date_of_birth = RequestBody.create(MediaType.parse("text/plain"), MyUtil.getStringDate(worker.getDate_of_birth()));
        RequestBody wages_rate_per_day = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(worker.getWages_rate_per_day()));
        RequestBody over_time_rate_per_hour = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(worker.getOver_time_rate_per_hour()));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("contact_no", contact_no);
        map.put("designation", designation);
        map.put("date_of_birth", date_of_birth);
        map.put("wages_rate_per_day", wages_rate_per_day);
        map.put("over_time_rate_per_hour", over_time_rate_per_hour);

        EmployeeClient client = ServiceGenerator.createService(EmployeeClient.class);
        client.updateWorker(worker.getProject(),worker.get_id(),part,map)
                .enqueue(new Callback<Worker>() {
                    @Override
                    public void onResponse(Call<Worker> call, Response<Worker> response) {
                        mView.hideDialog();
                        if(response.isSuccessful()){
                            mView.finishedAndSendWorker(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Worker> call, Throwable t) {
                        mView.hideDialog();
                    }
                });

    }
}
