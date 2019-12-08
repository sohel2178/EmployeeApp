package com.forbitbd.employeeman.ui.employee.employeeAttendance.dailyEmployeeAttendance;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;

import java.util.List;

public class DailyEmployeeAttendanceActivity extends PrebaseActivity implements DailyEmployeeAttendanceContract.View {

    private Project project;
    private EmployeeAttendanceResponse attendanceResponse;

    private DailyEmployeeAttendanceAdapter adapter;

    private DailyEmployeeAttendancePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_employee_attendance);

        mPresenter = new DailyEmployeeAttendancePresenter(this);

        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        this.attendanceResponse = (EmployeeAttendanceResponse) getIntent().getSerializableExtra(Constant.ATTENDANCE_RESPONSE);
        adapter = new DailyEmployeeAttendanceAdapter(this);
        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle("Attendance On "+getDate());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mPresenter.getDailyEmployeeAttendance(project.get_id(),attendanceResponse);
    }

    private String getDate(){
        String month = getResources().getStringArray(R.array.month_array)[attendanceResponse.get_id().getMonth()];
        return attendanceResponse.get_id().getDay()+"-"+month+"-"+attendanceResponse.get_id().getYear();
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
    public void renderAdapter(List<EmployeeAttendanceResponse> attendanceResponseList) {
        adapter.update(attendanceResponseList);
    }
}
