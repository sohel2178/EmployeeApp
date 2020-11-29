package com.forbitbd.employeeman.ui.employee.workerAttendance;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.androidutils.models.Project;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.androidutils.utils.AppPreference;
import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.employeeman.R;
import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.ui.employee.workerAttendance.attendance.AttendanceActivity;
import com.forbitbd.employeeman.ui.employee.workerAttendance.dailyWorkerAttendance.DailyWorkerAttendanceActivity;
import com.forbitbd.employeeman.utils.FileUtils;
import com.github.clans.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class WorkerAttendanceActivity extends PrebaseActivity implements
        WorkerAttendanceContract.View,
        View.OnClickListener {

    private static final int REQUEST_CODE=2000;
    private static final int READ_WRITE_PERMISSION=8000;

    private WorkerAttendancePresenter mPresenter;

    private SharedProject sharedProject;
    private AttendanceAdapter adapter;

    private int currentMonth,currentYear;

    private TextView tvStatus,tvNext,tvPrev;

    private FloatingActionButton fabAdd,fabDownload;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_attendance);
        mPresenter = new WorkerAttendancePresenter(this);
        this.sharedProject = (SharedProject) getIntent().getSerializableExtra(Constant.PROJECT);

        this.adapter = new AttendanceAdapter(this);

        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        this.currentYear = Calendar.getInstance().get(Calendar.YEAR);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle(sharedProject.getProject().getName()+" | "+"Attendances");

        setupBannerAd(R.id.adView);

        fabAdd = findViewById(R.id.fab_add);
        fabDownload = findViewById(R.id.fab_download);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        tvStatus = findViewById(R.id.status);
        tvNext = findViewById(R.id.next);
        tvPrev = findViewById(R.id.prev);

        tvNext.setOnClickListener(this);
        tvPrev.setOnClickListener(this);
        fabAdd.setOnClickListener(this);
        fabDownload.setOnClickListener(this);


        mPresenter.getMonthlyAttendance(sharedProject.getProject().get_id(),currentYear,currentMonth);

        if(sharedProject.getEmployee().isWrite()){
            fabAdd.setVisibility(View.VISIBLE);
        }else {
            fabAdd.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppPreference.getInstance(this).getCounter()>Constant.COUNTER){
            showInterAd();
        }
    }

    @Override
    public void onClick(View view) {

        if(view==fabAdd){
            mPresenter.startAddAttendanceActivity();
            AppPreference.getInstance(this).increaseCounter();
        }else if(view==fabDownload){
            requestFileAfterPermission();
        }else if(view==tvNext){
            increase();
            mPresenter.getMonthlyAttendance(sharedProject.getProject().get_id(),currentYear,currentMonth);

        }else if(view==tvPrev){
            decrease();
            mPresenter.getMonthlyAttendance(sharedProject.getProject().get_id(),currentYear,currentMonth);
        }
    }

    @Override
    public void startAddAttendanceActivity() {
        Intent intent = new Intent(getApplicationContext(), AttendanceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject.getProject());
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_CODE);
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

        tvStatus.setText(getStringDate());

        adapter.clear();
        for (AttendanceResponse x: attendanceResponseList){
            adapter.addAttendanceResponse(x);
        }
    }

    @Override
    public void startDailyAttendanceActivity(AttendanceResponse attendanceResponse) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PROJECT,sharedProject.getProject());
        bundle.putSerializable(Constant.ATTENDANCE_RESPONSE,attendanceResponse);
        Intent intent = new Intent(getApplicationContext(), DailyWorkerAttendanceActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void openDownloadedFile(String path) {
        FileUtils.openFile(this,path);
        //openFile(path);
    }

    @Override
    public String saveFile(ResponseBody responseBody, int year, int month) {

        String directory = FileUtils.getDirectory("Construction Manager",sharedProject.getProject().getName(),"Workers");

        String fileName = getStringDate()+".xlsx";
        Log.d("HHHHHH",fileName);
        Log.d("HHHHHH",directory);

        String path = FileUtils.saveFile(directory,fileName,responseBody);
        Log.d("HHHHHH",path);

        return path;


        //Log.d("UUUUUUUU", );
       // return saveMonthlyAttendanceFile(project.getName(),"Worker",responseBody,year,month);
        //return saveTaskFile("Construction Manager",project.getName(),"Workers",getStringDate().concat(".xlsx"),responseBody);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            AttendanceResponse attendanceResponse = (AttendanceResponse) data.getSerializableExtra(Constant.ATTENDANCE_RESPONSE);

            if(currentYear==attendanceResponse.get_id().getYear() && currentMonth==attendanceResponse.get_id().getMonth()){
                adapter.positionItem(attendanceResponse);
            }


        }
    }

    @AfterPermissionGranted(READ_WRITE_PERMISSION)
    private void requestFileAfterPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            //sendDownloadRequest();
            mPresenter.downloadMonthlyWorkerAttendance(sharedProject.getProject().get_id(),currentYear,currentMonth);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "App need to Permission for Read and Write",
                    READ_WRITE_PERMISSION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    private String getStringDate(){
        String month = getResources().getStringArray(R.array.month_array)[currentMonth];
        return month+" - "+currentYear;
    }

    private void increase(){
        currentMonth++;
        if(currentMonth>11){
            currentYear++;
            currentMonth= currentMonth%12;
        }
    }

    private void decrease(){
        currentMonth--;
        if(currentMonth<0){
            currentYear--;
            currentMonth= currentMonth+12;
        }
    }
}
