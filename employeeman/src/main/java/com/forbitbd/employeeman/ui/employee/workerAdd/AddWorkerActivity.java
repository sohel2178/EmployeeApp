package com.forbitbd.employeeman.ui.employee.workerAdd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.forbitbd.androidutils.dialog.DatePickerListener;
import com.forbitbd.androidutils.dialog.MyDatePickerFragment;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Worker;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class AddWorkerActivity extends PrebaseActivity implements AddWorkerContract.View, View.OnClickListener {

    private TextInputLayout tiName,tiContact,tiDesignation,tiWagesPerDay,tiOvertimePerHour,tiDateOfBirth;
    private EditText etName,etContact,etDesignation,etWagesPerDay,etOvertimePerHour,etDateOfBirth;
    private Button btnBrowse,btnSave;
    private ImageView ivImage;

    private AddWorkerPresenter mPresenter;

    private Project project;
    private Worker worker;
    private byte[] bytes;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);
        mPresenter = new AddWorkerPresenter(this);

        this.worker = (Worker) getIntent().getSerializableExtra(Constant.WORKER);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);



        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        setupBannerAd(R.id.adView);

        tiName = findViewById(R.id.ti_name);
        tiContact = findViewById(R.id.ti_contact);
        tiDesignation = findViewById(R.id.ti_designation);
        tiWagesPerDay = findViewById(R.id.ti_wages_rate_per_day);
        tiOvertimePerHour = findViewById(R.id.ti_over_time_rate_per_hour);
        tiDateOfBirth = findViewById(R.id.ti_date_of_birth);

        etName = findViewById(R.id.name);
        etContact = findViewById(R.id.contact);
        etDesignation = findViewById(R.id.designation);
        etWagesPerDay = findViewById(R.id.wages_rate_per_day);
        etOvertimePerHour = findViewById(R.id.over_time_rate_per_hour);
        etDateOfBirth = findViewById(R.id.date_of_birth);
        ivImage = findViewById(R.id.image);

        btnBrowse = findViewById(R.id.browse);
        btnSave = findViewById(R.id.save);

        btnBrowse.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        etDateOfBirth.setOnClickListener(this);

        if(this.worker!=null){
            mPresenter.bindWorker(this.worker);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(worker==null){
            setTitle("Worker Entry Form");
        }else{
            setTitle("Worker update Form");
        }

        if(AppPreference.getInstance(this).getCounter()>Constant.COUNTER){
            showInterAd();
        }
    }

    @Override
    public void onClick(View view) {

        if(view==etDateOfBirth){
            mPresenter.dateClick();
        }else if(view==btnBrowse){
            mPresenter.browseClick();
        }else if(view==btnSave){
            mPresenter.checkAndSave();
        }
    }


    @Override
    public void openCropImageActivity() {
        openCropImageActivity(true);
    }

    @Override
    public void openCalendar() {
        MyDatePickerFragment myDateDialog = new MyDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE,getString(R.string.select_completion_date));
        myDateDialog.setArguments(bundle);
        myDateDialog.setCancelable(false);
        myDateDialog.setDatePickerListener(new DatePickerListener() {
            @Override
            public void onDatePick(long time) {
                etDateOfBirth.setText(MyUtil.getStringDate(new Date(time)));
            }
        });
        myDateDialog.show(getSupportFragmentManager(),"FFFF");
    }

    @Override
    public void checkAndSave() {
        if(this.worker==null){
            this.worker = new Worker();
        }
        this.worker.setProject(project.get_id());
        this.worker.setName(etName.getText().toString().trim());
        this.worker.setContact_no(etContact.getText().toString().trim());
        this.worker.setDesignation(etDesignation.getText().toString().trim());
        String wagesPerDay = etWagesPerDay.getText().toString().trim();
        String overtimePerHour = etOvertimePerHour.getText().toString().trim();

        try{
            this.worker.setWages_rate_per_day(Double.parseDouble(wagesPerDay));
            this.worker.setOver_time_rate_per_hour(Double.parseDouble(overtimePerHour));
        }catch (Exception e){
            Log.d("HHHHHH","eee");
        }


        boolean valid = mPresenter.validate(worker);
        if(!valid) {
            return;
        }

        try {
            this.worker.setDate_of_birth(MyUtil.getDate(etDateOfBirth.getText().toString().trim()));
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Date of Birth is no Selected", Toast.LENGTH_SHORT).show();
            return;
        }

        if(ivImage.getDrawable()==null){
            Toast.makeText(this, "Browse to Select an Worker Image", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isOnline()){
            Toast.makeText(this, "Turn on Your Internet Connection to Perform this Operations", Toast.LENGTH_SHORT).show();
        }else{
            if(worker.get_id()==null){
                mPresenter.saveWorker(worker,bytes);
            }else{
                mPresenter.updateWorker(worker,bytes);
            }
        }
    }

    @Override
    public void bindWorker(Worker worker) {
        etName.setText(worker.getName());
        etContact.setText(worker.getContact_no());
        etDesignation.setText(worker.getDesignation());
        etDateOfBirth.setText(MyUtil.getStringDate(worker.getDate_of_birth()));
        etWagesPerDay.setText(String.valueOf(worker.getWages_rate_per_day()));
        etOvertimePerHour.setText(String.valueOf(worker.getOver_time_rate_per_hour()));

        if(!worker.getImage().equals("") && worker.getImage()!=null){
            Picasso.with(getApplicationContext())
                    .load(worker.getImage())
                    .into(ivImage);
        }

        btnSave.setText(getString(R.string.update));
    }

    @Override
    public void clearPreError() {
        tiName.setErrorEnabled(false);
        tiContact.setErrorEnabled(false);
        tiDesignation.setErrorEnabled(false);
        tiDateOfBirth.setErrorEnabled(false);
        tiWagesPerDay.setErrorEnabled(false);
        tiOvertimePerHour.setErrorEnabled(false);
    }

    @Override
    public void showValidationError(String message, int fieldId) {
        switch (fieldId){
            case 1:
                etName.requestFocus();
                tiName.setError(message);
                break;

            case 2:
                etContact.requestFocus();
                tiContact.setError(message);
                break;

            case 4:
                etDesignation.requestFocus();
                tiDesignation.setError(message);
                break;

            case 5:
                etWagesPerDay.requestFocus();
                tiWagesPerDay.setError(message);
                break;

            case 6:
                etOvertimePerHour.requestFocus();
                tiOvertimePerHour.setError(message);
                break;

        }

    }

    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void hideDialog() {
        hideProgressDialog();
    }

    @Override
    public void finishedAndSendWorker(Worker worker) {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.WORKER,worker);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void showErrorToast(String message) {
        Log.d("UUUUU",message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                    Bitmap scaledBitMap = MyUtil.getScaledBitmap(bitmap,200,200);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    scaledBitMap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos);
                    bytes = bos.toByteArray();
                    ivImage.setImageBitmap(scaledBitMap);

                    Log.d("UUUUU",ivImage.getDrawable()+"");


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
