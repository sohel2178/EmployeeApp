package com.forbitbd.employeeman.ui.employee.employeeAttendance.employeeAddAttendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.androidutils.dialog.DatePickerListener;
import com.forbitbd.androidutils.dialog.MyDatePickerFragment;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.Employee;
import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class EmployeeAddAttendanceActivity extends PrebaseActivity implements EmployeeAddAttendanceContract.View, View.OnClickListener {

    private Project project;
    private EmployeeAddAttendancePresenter mPresenter;

    private EmployeeAddAttendanceAdapter adapter;

    private EditText etDate;
    MaterialButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add_attendance);

        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        this.mPresenter = new EmployeeAddAttendancePresenter(this);

        adapter = new EmployeeAddAttendanceAdapter(this);

        initView();
    }

    private void initView() {

        setupToolbar(R.id.toolbar);

        etDate = findViewById(R.id.date);
        etDate.setText(MyUtil.getStringDate(new Date()));
        etDate.setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

         btnSave = findViewById(R.id.save);
        btnSave.setOnClickListener(this);


        mPresenter.getAllProjectEmployee(project.get_id());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Employee Attendance Sheet");
    }

    @Override
    public void onClick(View view) {
        if(view==etDate){
            mPresenter.openCalendar();
        }else if(view==btnSave){
            mPresenter.saveClick();
        }

    }

    @Override
    public void openCalendar() {
        MyDatePickerFragment myDateDialog = new MyDatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE,getString(R.string.select_attendance_date));
        try {
            bundle.putLong(Constant.TIME,MyUtil.getDate(etDate.getText().toString().trim()).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myDateDialog.setArguments(bundle);
        myDateDialog.setCancelable(false);
        myDateDialog.setDatePickerListener(new DatePickerListener() {
            @Override
            public void onDatePick(long time) {
                etDate.setText(MyUtil.getStringDate(new Date(time)));
            }
        });
        myDateDialog.show(getSupportFragmentManager(),"FFFF");

    }

    @Override
    public void saveClick() {
        try {
            Date date = MyUtil.getDate(etDate.getText().toString().trim());
            mPresenter.uploadEmployeeAttendanceToServer(adapter.getEmployeeList(),date,project.get_id());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void renderEmployeeList(List<Employee> employeeList) {
        adapter.clear();

        for (Employee x: employeeList){
            adapter.addEmployee(x);
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
    public void back(EmployeeAttendanceResponse attendanceResponse) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.ATTENDANCE_RESPONSE,attendanceResponse);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
