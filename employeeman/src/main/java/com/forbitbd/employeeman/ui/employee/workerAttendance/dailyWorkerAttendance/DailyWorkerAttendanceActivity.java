package com.forbitbd.employeeman.ui.employee.workerAttendance.dailyWorkerAttendance;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.AttendanceResponse;

import java.util.List;

public class DailyWorkerAttendanceActivity extends PrebaseActivity implements DailyWorkerAttendanceContract.View {

    private Project project;
    private AttendanceResponse attendanceResponse;

    private WorkerAttendanceAdapter adapter;

    private DailyWorkerAttendancePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_worker_attendance);

        mPresenter = new DailyWorkerAttendancePresenter(this);

        this.project = (Project) getIntent().getSerializableExtra(Constant.PROJECT);
        this.attendanceResponse = (AttendanceResponse) getIntent().getSerializableExtra(Constant.ATTENDANCE_RESPONSE);
        adapter = new WorkerAttendanceAdapter(this);
        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle("Attendance On "+getDate());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        mPresenter.getDailyAttendance(project.get_id(),attendanceResponse);

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
    public void renderAdapter(List<AttendanceResponse> attendanceResponseList) {
        adapter.update(attendanceResponseList);
    }
}
