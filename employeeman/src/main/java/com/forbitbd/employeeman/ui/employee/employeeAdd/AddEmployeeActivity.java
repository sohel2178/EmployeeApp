package com.forbitbd.employeeman.ui.employee.employeeAdd;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.User;
import com.forbitbd.androidutils.ui.searchUser.SearchUserActivity;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.Employee;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

public class AddEmployeeActivity extends PrebaseActivity implements
        AddEmployeeContract.View, View.OnClickListener {

    private static final int REQUEST_CODE =10000;

    private TextInputLayout tiSalary,tiDesignation;
    private EditText etSalary,etDesignation;
    private Button btnBrowse,btnSave;
    private ImageView ivImage;
    private TextView tvName,tvContact;

    private AddEmployeePresenter mPresenter;
    private Project project;
    private Employee employee;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        mPresenter = new AddEmployeePresenter(this);

        this.employee = (Employee) getIntent().getSerializableExtra(Constant.EMPLOYEE);
        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);

        setupBannerAd(R.id.adView);

        tiSalary = findViewById(R.id.ti_salary);
        tiDesignation = findViewById(R.id.ti_designation);

        etSalary = findViewById(R.id.salary);
        etDesignation = findViewById(R.id.designation);

        ivImage = findViewById(R.id.image);
        tvName = findViewById(R.id.name);
        tvContact = findViewById(R.id.contact);

        btnBrowse = findViewById(R.id.btn_select_employee);
        btnSave = findViewById(R.id.save);

        btnBrowse.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        if(this.employee!=null){
            mPresenter.bind();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(this.employee!=null){
            setTitle("Employee Update Form");
        }else{
            setTitle("Employee Entry Form");
        }

    }

    @Override
    public void startSearchUserActivity() {
        Intent intent = new Intent(getApplicationContext(), SearchUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,project);
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void checkAndSave() {

        if(this.employee==null){
            this.employee = new Employee();
        }
        employee.setProject(project.get_id());

        try {
            this.employee.setSalary(Double.parseDouble(etSalary.getText().toString().trim()));
        }catch (Exception e){
            e.fillInStackTrace();
        }

        this.employee.setDesignation(etDesignation.getText().toString().trim());

        boolean valid = mPresenter.validate(employee);
        if(!valid) {
            return;
        }

        if(!isOnline()){
            Toast.makeText(this, "Turn on Your Internet Connection to Perform this Operations", Toast.LENGTH_SHORT).show();
        }else{
            if(employee.get_id()==null){
                mPresenter.saveEmployee(employee);
            }else{
                mPresenter.updateEmployee(employee);
            }

        }

    }

    @Override
    public void clearPreError() {
        tiSalary.setErrorEnabled(false);
        tiDesignation.setErrorEnabled(false);
    }

    @Override
    public void showValidationError(String message, int fieldId) {

        switch (fieldId) {
            case 4:
                etSalary.requestFocus();
                tiSalary.setError(message);
                break;

            case 5:
                etDesignation.requestFocus();
                tiDesignation.setError(message);
                break;
        }

    }

    @Override
    public void finishedAndSendEmployee(Employee employee) {
        Log.d("HHHHHHHH","finishedAndSendEmployee Called");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.EMPLOYEE,employee);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);

        finish();

    }

    @Override
    public void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void bind() {

        if(employee.getImage()!=null && !employee.getImage().equals("")){
            Picasso.with(getApplicationContext())
                    .load(employee.getImage())
                    .into(ivImage);
        }



        tvName.setText(employee.getName());
        tvContact.setText(employee.getContact_no());

        btnBrowse.setVisibility(View.GONE);

        etDesignation.setText(employee.getDesignation());
        etSalary.setText(String.valueOf(employee.getSalary()));
        btnSave.setText(getString(R.string.update));
    }

    @Override
    public void onClick(View view) {
        if(view==btnBrowse){
            mPresenter.browseClick();
        }else if(view==btnSave){
            mPresenter.checkAndSave();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
       if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
           User user = (User) data.getSerializableExtra(Constant.USER);

           if(user!=null){
               if(employee== null){
                   employee = new Employee();
               }
               employee.setUser(user);
               mPresenter.bind();
               btnSave.setText(getString(R.string.save));
           }
       }
    }
}
